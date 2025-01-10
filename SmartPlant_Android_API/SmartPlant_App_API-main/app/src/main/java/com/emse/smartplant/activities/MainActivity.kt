package com.emse.smartplant.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emse.smartplant.R
import com.emse.smartplant.ui.theme.SmartPlantTheme


class MainActivity : ComponentActivity() {

    companion object {
        const val PLANT_PARAM = "com.emse.smartplant.activities"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onButtonClick: (String) -> Unit = { name ->
            // Here you can access to the activity state (ie baseContext)
            val intent = Intent(this, PlantListActivity::class.java)
            Toast.makeText(baseContext, "Hello $name !", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }


        setContent {
            SmartPlantTheme() {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        onClick = onButtonClick,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}




/**
 * This function allows to display the app logo when greeting the user
 */


@Composable
fun AppLogo(modifier: Modifier) {
    Image(
        painter = painterResource(R.drawable.app_logo),
        contentDescription = stringResource(R.string.app_logo_description),
        modifier = modifier.paddingFromBaseline(top = 100.dp).height(80.dp),
    )
}

/**
 * This function allows to enter the name
 */

@Composable
fun Greeting(onClick: (String) -> Unit, modifier: Modifier = Modifier) {
    Column {
        AppLogo(Modifier.padding(top = 32.dp).fillMaxWidth())
        Text(
            stringResource(R.string.app_main_welcome),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )
        var name by remember { mutableStateOf("") }

        OutlinedTextField(
            name,
            onValueChange = { name = it },
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            placeholder = {
                Row {
                    Icon(
                        Icons.Rounded.AccountCircle,
                        stringResource(R.string.act_main_fill_name),
                        Modifier.padding(end = 8.dp),
                    )
                    Text(stringResource(R.string.act_main_fill_name))
                }

            })

        Button(
            onClick = { onClick(name) },
            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally)
        ) {
           Text(stringResource(R.string.act_main_open))
        }
    }
}

