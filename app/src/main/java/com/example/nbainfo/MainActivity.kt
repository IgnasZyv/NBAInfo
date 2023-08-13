package com.example.nbainfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nbainfo.ui.theme.NBAInfoTheme
import com.example.nbainfo.viewmodels.HomeViewModel
import com.example.nbainfo.viewmodels.PlayerViewModel
import com.example.nbainfo.viewmodels.TeamViewModel
import com.example.nbainfo.views.MainContent
import com.example.nbainfo.views.NavigationBar
import com.example.nbainfo.views.PlayerMainContent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NBAInfoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // create the view models
                    val homeViewModel: HomeViewModel by viewModels()
                    val teamViewModel: TeamViewModel by viewModels()
                    val playerViewModel: PlayerViewModel by viewModels()

                    // create a nav controller
                    val navController = rememberNavController()

                    Scaffold (
                        // show the bottom bar on all screens
                        bottomBar = {
                            NavigationBar(navController = navController)
                        }
                    ) {
                        Column (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        ) {
                            // create a nav host and pass the nav controller
                            NavHost(navController = navController, startDestination = "home") {
                                // create a composable for the home screen
                                composable("home") {
                                    MainContent(homeViewModel = homeViewModel, teamViewModel = teamViewModel)
                                }
                                composable("players") {
                                    PlayerMainContent(playerViewModel = playerViewModel)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

