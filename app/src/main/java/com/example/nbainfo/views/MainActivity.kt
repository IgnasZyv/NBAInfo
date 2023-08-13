package com.example.nbainfo.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nbainfo.R
import com.example.nbainfo.models.SortCriteria
import com.example.nbainfo.models.Team
import com.example.nbainfo.ui.theme.NBAInfoTheme
import com.example.nbainfo.viewmodels.HomeViewModel
import com.example.nbainfo.viewmodels.TeamViewModel

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
                                    PlayerMainContent()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

