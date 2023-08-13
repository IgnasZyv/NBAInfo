package com.example.nbainfo.views

import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nbainfo.R
import com.example.nbainfo.models.SortCriteria
import com.example.nbainfo.models.Team
import com.example.nbainfo.viewmodels.HomeViewModel
import com.example.nbainfo.viewmodels.TeamViewModel

@Composable
fun MainContent(homeViewModel: HomeViewModel, teamViewModel: TeamViewModel) {

    // get the list of teams from the view model and pass it to the Teams composable
    val teams: List<Team> by teamViewModel.teams.observeAsState(emptyList())

    // states for the bottom sheet
    val (selectedTeam, setSelectedTeam) = remember { mutableStateOf<Team?>(null) }
    val (isBottomSheetOpen, setBottomSheetOpen) = remember { mutableStateOf(false) }

    if (selectedTeam != null) {
        BottomSheet(
            isOpen = isBottomSheetOpen,
            selectedTeamId = selectedTeam.id,
            teamViewModel = teamViewModel,
        ) { setBottomSheetOpen(false) }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header(homeViewModel = homeViewModel, teamViewModel = teamViewModel)
        ListTitles()
        // pass the list of teams to the Teams composable and set the selected team when the user clicks on a team
        Teams(data = teams) {clickedTeam ->
            setSelectedTeam(clickedTeam)
            setBottomSheetOpen(true)
        }
    }
}

@Composable
fun Header(homeViewModel: HomeViewModel, teamViewModel: TeamViewModel) {
    // state for the sort dialog to open
    var openDialog by remember { mutableStateOf(false) }
    // get the sort criteria from the home view model
    val sortText = homeViewModel.sortText.value
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Home",
            style = MaterialTheme.typography.titleLarge,
        )
        ElevatedButton(
            // open the dialog when the user clicks the button
            onClick = { openDialog = true },
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sort_button_white_foreground),
                    contentDescription = "SortButton",
                    modifier = Modifier
                        .size(23.dp)
                )

                Text(
                    text = sortText,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            }
        }
    }

    val context = LocalContext.current

    // show the sort dialog when the state is true
    SortDialog(
        openDialog = openDialog,
        // when the user selects a criteria, sort the teams and change the text in the header
        onSortSelected = {
            teamViewModel.sortTeamsBy(it)
            homeViewModel.setSort(it)
            openDialog = false
            Toast.makeText(context, "Sorted by $it", Toast.LENGTH_SHORT).show()
        },
        // close the dialog when the user clicks outside of it
        onDismiss = { openDialog = false }
    )
}

@Composable
fun ListTitles() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Name",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "City",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Conference",
                style = MaterialTheme.typography.titleMedium,
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun Teams(data: List<Team>, onTeamClick: (Team) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 0.dp, start = 15.dp, end = 15.dp)
    ) {
        items(data) { team ->
            // List all the teams by passing each team to the ListTeam composable
            Column {
                ListTeam(team = team, onTeamClick = onTeamClick)
            }
        }
    }

}

@Composable
fun ListTeam(team: Team, onTeamClick: (Team) -> Unit) {
    // split full name into two words
    val fullNameWords = team.full_name.split(" ")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
            .clickable {
                onTeamClick(team)
            },
    ) {
        // List each team's name, city, and conference in a row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = fullNameWords[0],
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = fullNameWords[1],
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Text(text = team.city)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = team.conference)
                Image(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Arrow")
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortDialog(
    openDialog: Boolean,
    onSortSelected: (SortCriteria) -> Unit,
    onDismiss: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = { onSortSelected(SortCriteria.Name) }) {
                    Text(text = "Name")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = { onSortSelected(SortCriteria.City) }) {
                    Text(text = "City")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = { onSortSelected(SortCriteria.Conference) }) {
                    Text(text = "Conference")
                }
            }
        }
    }
}