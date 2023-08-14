package com.example.nbainfo.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavigationBar(navController: NavController) {
    // keep track of the current screen
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    // get the current screen from the back stack entry
    val currentScreen = currentBackStackEntry?.destination?.route
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = {
                // navigate to the home screen if not already there
                if (currentScreen != "home") {
                    navController.navigate("home")
                }
                      },
            // change the colors based on the current screen
            colors = ButtonDefaults.buttonColors(
                containerColor = if (currentScreen == "home") MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary,
                contentColor = if (currentScreen == "home") MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary,
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(imageVector = Icons.Default.Home, contentDescription = "Home")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "Home",
                )
            }
        }
        Spacer(modifier = Modifier.padding(25.dp))
        Button(
            onClick = {
                if (currentScreen != "players") {
                    navController.navigate("players")
                }
                      },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (currentScreen == "players") MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary,
                contentColor = if (currentScreen == "players") MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary,
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(imageVector = Icons.Default.Person, contentDescription = "Players")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "Players")
            }
        }
    }
}