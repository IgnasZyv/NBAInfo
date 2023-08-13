package com.example.nbainfo.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.example.nbainfo.models.Player
import com.example.nbainfo.models.SearchCriteria
import com.example.nbainfo.models.Team
import com.example.nbainfo.viewmodels.PlayerViewModel
import com.example.nbainfo.viewmodels.TeamViewModel

@Composable fun PlayerMainContent(playerViewModel: PlayerViewModel, teamViewModel: TeamViewModel) {
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

    Column {
        Header(playerViewModel = playerViewModel)
        ListPlayerTitles()
        ListPlayers(playerViewModel = playerViewModel) { player ->
            setSelectedTeam(player.team)
            setBottomSheetOpen(true)
        }
    }
}

@Composable
fun Header(playerViewModel: PlayerViewModel) {
    var openDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = "Players",
            style = MaterialTheme.typography.titleLarge,
        )
        ElevatedButton(onClick = { openDialog = true }) {
            Image(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)

            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "Search")
        }
    }

    SearchDialog(
        playerViewModel = playerViewModel,
        openDialog = openDialog,
        onDismiss = { openDialog = false }
    )
}

@Composable
fun ListPlayerTitles() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = "First Name", style = MaterialTheme.typography.titleMedium)
            Text(text = "LastName", style = MaterialTheme.typography.titleMedium)
            Text(text = "Team", style = MaterialTheme.typography.titleMedium)

        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ListPlayers(playerViewModel: PlayerViewModel, onPlayerClick : (Player) -> Unit) {
    // if search is active, display searchedPlayers, else players
    val players: List<Player> by if (playerViewModel.searchActive.value) {
        playerViewModel.searchedPlayers.observeAsState(emptyList())
    } else {
        playerViewModel.players.observeAsState(emptyList())
    }
    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = playerViewModel.searchActive.value,
                onCheckedChange = { playerViewModel.searchActive.value = it }
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text("Search")
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp, bottom = 0.dp, start = 15.dp, end = 15.dp)
    ) {
        items(players) { player ->
            Column {
                ListPlayer(player = player, onPlayerClick = onPlayerClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPlayer(player: Player, onPlayerClick: (Player) -> Unit) {
    val teamNameSplit = player.team.full_name.split(" ")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        onClick = { onPlayerClick(player) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = player.first_name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.width(75.dp)
            )
            Text(text = player.last_name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.width(65.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = teamNameSplit[0], style = MaterialTheme.typography.titleMedium)
                    Text(text = teamNameSplit[1], style = MaterialTheme.typography.titleMedium)
                }
                Image(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Arrow")
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDialog(
    playerViewModel: PlayerViewModel,
    openDialog: Boolean,
    onDismiss: () -> Unit
) {
    // save the text field value in this variable
    var searchText by remember { mutableStateOf("") }
    var selectedSearchCriteria by remember { mutableStateOf(SearchCriteria.Name) }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
        ) {
            Column(
                Modifier.background(MaterialTheme.colorScheme.surface),
            ) {
                // display the radio buttons for the search criteria
                SearchRadioButtons(
                    selectedSearchCriteria = selectedSearchCriteria,
                    // update the selected search criteria when a radio button is selected
                    onCriteriaSelected = { newCriteria ->
                        selectedSearchCriteria = newCriteria
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = searchText,
                        placeholder = { Text("Search") },
                        onValueChange = { searchText = it },
                        modifier = Modifier.weight(7f),
                        singleLine = false,
                        keyboardActions = KeyboardActions(
                            onDone = {
                                playerViewModel.searchForPlayers(searchText, selectedSearchCriteria)
                                onDismiss()
                            }
                        ),
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    ElevatedButton(
                        onClick = {
                            // call the search function with the search text and criteria
                            playerViewModel.searchForPlayers(searchText, selectedSearchCriteria)
                            // close the dialog
                            onDismiss()
                            searchText = ""
                        },
                        modifier = Modifier.weight(2f)
                    ) {
                        Image(imageVector = Icons.Default.Send,
                            contentDescription = "Confirm",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            }


        }
    }
}

@Composable fun SearchRadioButtons(selectedSearchCriteria : SearchCriteria, onCriteriaSelected : (SearchCriteria) -> Unit) {
    val searchCriteria = SearchCriteria.values()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        searchCriteria.forEach { criteria ->
            Row(
                modifier = Modifier
                    .clickable { onCriteriaSelected(criteria) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = criteria == selectedSearchCriteria,
                    onClick = { onCriteriaSelected(criteria) },
                )
                Text(text = criteria.name)
            }
        }
    }

}

