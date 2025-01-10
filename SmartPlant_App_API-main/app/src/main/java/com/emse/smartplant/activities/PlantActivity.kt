package com.emse.smartplant.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emse.smartplant.R
import com.emse.smartplant.SmartPlantTopAppBar
import com.emse.smartplant.models.PlantDto
import com.emse.smartplant.models.PlantViewModel
import com.emse.smartplant.services.PlantService
import com.emse.smartplant.ui.theme.SmartPlantTheme


class PlantActivity:  ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel: PlantViewModel by viewModels()
        val param = intent.getStringExtra(MainActivity.PLANT_PARAM)
        viewModel.plant = PlantService.findByNameOrId(param)




        setContent {
            SmartPlantTheme() {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        SmartPlantTopAppBar(
                            returnAction = { finish() } // Allows to go back to the plant list

                        )
                    }


                ) { innerPadding ->
                    if (viewModel.plant != null) {
                        PlantDetail(viewModel, Modifier.padding(innerPadding))
                    } else {
                        NoPlant(Modifier.padding(innerPadding))
                    }


                }
            }
        }
    }
}

@Composable
fun PlantDetail(model: PlantViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {

        OutlinedTextField(
            value = model.plant?.name ?: "",
            onValueChange = { model.plant?.name = it },
            label = { Text(text = stringResource(R.string.act_plant_name)) },
            modifier = Modifier.fillMaxWidth(),
        )
        // Current Temperature

        OutlinedTextField(
            value = model.plant?.current_temperature.toString() +  "°C",
            onValueChange = { newValue ->
                model.plant?.current_temperature = (newValue.toFloatOrNull() ?: 0f).toDouble()
            },
            label = { Text(text = stringResource(R.string.act_plant_current_temperature)) },
            modifier = Modifier.fillMaxWidth(),
        )


        // Current Humidity

        OutlinedTextField(
            value = model.plant?.current_humidity.toString() + "%",
            onValueChange = { newValue ->
                model.plant?.current_humidity = ((newValue.toIntOrNull() ?: 0) as Double?)!!
            },
            label = { Text(text = stringResource(R.string.act_plant_current_humidity)) },
            modifier = Modifier.fillMaxWidth(),
        )
        // Current Enlightment

        OutlinedTextField(
            value = model.plant?.current_enlightment.toString() + "%",
            onValueChange = { newValue ->
                model.plant?.current_enlightment = (newValue.toIntOrNull() ?: 0).toDouble()},
            label = { Text(text = stringResource(R.string.act_plant_current_enlightment)) },
            modifier = Modifier.fillMaxWidth(),
        )
        thirstyPlant(model,modifier)
    }

}

@Composable
fun thirstyPlant(model: PlantViewModel, modifier: Modifier = Modifier){
    val plant_view = model.plant
    if (plant_view != null) {
        if (plant_view.current_humidity < plant_view.min_humidity){
            Text(
                text = stringResource(R.string.water_it),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        else if (plant_view.current_humidity > plant_view.max_humidity){
            Text(
                text = stringResource(R.string.drowned_it),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        else{
            Text(
                text = stringResource(R.string.plant_fine),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun NoPlant(modifier: Modifier = Modifier){
    Text(
        text = stringResource(R.string.act_plant_none),
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier.padding(16.dp)
    )
}



