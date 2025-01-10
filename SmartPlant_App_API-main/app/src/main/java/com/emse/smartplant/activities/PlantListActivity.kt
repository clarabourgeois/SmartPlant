package com.emse.smartplant.activities



import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emse.smartplant.SmartPlantTopAppBar
import com.emse.smartplant.models.PlantDto
import com.emse.smartplant.models.PlantViewModel
import com.emse.smartplant.services.PlantService
import com.emse.smartplant.ui.theme.PurpleGrey80
import com.emse.smartplant.ui.theme.SmartPlantTheme
import kotlinx.coroutines.flow.asStateFlow


class PlantListActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel: PlantViewModel by viewModels()


        setContent{
            val plantState by viewModel.plantState.asStateFlow().collectAsState()
            LaunchedEffect(Unit) {
                viewModel.findAll()
            }
            if (plantState.error != null) {
                setContent {
                    PlantListDisplay(
                        plants = emptyList(),
                        navigateBack = { finish() },
                        openPlant = { }
                    )
                }
                Toast
                    .makeText(applicationContext, "Error on plants loading ${plantState.error}", Toast.LENGTH_LONG)
                    .show()
            } else {
                PlantListDisplay(
                    plants = plantState.rooms,
                    navigateBack = { finish() },
                    openPlant = { plantId -> openPlant(this@PlantListActivity, plantId.toString()) })
            }


        }

    }

    private fun openPlant(context: android.content.Context, plantParam: String) {
        val intent = Intent(context, PlantActivity::class.java).apply {
            putExtra(MainActivity.PLANT_PARAM, plantParam)
        }
        context.startActivity(intent)
    }
}

@Composable
fun PlantListDisplay(
    plants: List<PlantDto>,
    navigateBack: () -> Unit,
    openPlant: (id: Long) -> Unit
) {
    SmartPlantTheme {
        Scaffold(
            topBar = { SmartPlantTopAppBar("Plants", navigateBack) }
        ) { innerPadding ->
            if (plants.isEmpty()) {
                Text(
                    text = "No plant found",
                    modifier = Modifier.padding(innerPadding)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(innerPadding),
                ) {
                    items(plants, key = { it.id }) {
                        PlantItem(
                            plant = it,
                            modifier = Modifier.clickable { openPlant(it.id) },
                        )
                    }
                }
            }
        }
    }
}




@Composable
fun PlantItem(plant: PlantDto, modifier: Modifier = Modifier) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, PurpleGrey80)
    ) {
        Row(
            modifier = modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = plant.plant_type,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Current humidity : " + (plant.current_humidity.toString() ?: "?") + "%",
                    style = MaterialTheme.typography.bodySmall
                )


            }
            Text(
                text = (plant.name ?: "?") ,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlantItemPreview() {
    SmartPlantTheme() {
        PlantItem(PlantService.PLANTS[0])
    }
}
