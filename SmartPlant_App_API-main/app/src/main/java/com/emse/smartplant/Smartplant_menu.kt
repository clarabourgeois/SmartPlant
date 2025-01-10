package com.emse.smartplant

import android.content.Intent
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import com.emse.smartplant.activities.MainActivity
import com.emse.smartplant.activities.PlantListActivity
import com.emse.smartplant.ui.theme.SmartPlantTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SmartPlantTopAppBar(title: String? = null, returnAction: () -> Unit = {},   currentPage: String = "",onNavigateToPage: (String) -> Unit = {}) {
    val colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.primary,
    )

    // Intent going back to the app's main menu
    val context = LocalContext.current
    val intentMain = remember { Intent(context, MainActivity::class.java) }
    val intentList = remember { Intent(context, PlantListActivity::class.java) }



    // Define the actions displayed on the right side of the app bar
    val actions: @Composable RowScope.() -> Unit = {
        IconButton(onClick =  { context.startActivity(intentList)}) {
            val isCurrentPage = currentPage == "PlantList"

            Icon(
                painter = painterResource(R.drawable.ic_list),
                contentDescription = stringResource(R.string.app_go_plantlist) ,
                tint = if (isCurrentPage) MaterialTheme.colorScheme.secondary else LocalContentColor.current
            )
        }
        IconButton(onClick =  { context.startActivity(intentMain)}) {
            Icon(
                painter = painterResource(R.drawable.ic_action_name),
                contentDescription = stringResource(R.string.app_go_plantlist)
            )
        }
        // To create a new plant (possibly)
        IconButton(onClick =  {}) {
            Icon(
                painter = painterResource(R.drawable.ic_add_plant),
                contentDescription = stringResource(R.string.app_go_plantlist)
            )
        }

    }
    // Display the app bar with the title if present and actions
    if(title == null) {
        TopAppBar(
            title = { Text("") },
            colors = colors,
            actions = actions
        )
    } else {
        MediumTopAppBar(
            title = { Text(title) },
            colors = colors,
            // The title will be displayed in other screen than the main screen.
            // In this case we need to add a return action
            navigationIcon = {
                IconButton(onClick = returnAction) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.app_go_back_description)
                    )
                }
            },
            actions = actions
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SmartPlantTopAppBarHomePreview() {
    SmartPlantTheme() {
        SmartPlantTopAppBar(null)
    }
}

@Preview(showBackground = true)
@Composable
fun SmartPlantTopAppBarPreview() {
    SmartPlantTheme() {
        SmartPlantTopAppBar("A page")
    }
}