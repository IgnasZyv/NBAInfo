package com.example.nbainfo.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nbainfo.viewmodels.TeamViewModel
import com.example.nbainfo.models.Team
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    isOpen: Boolean,
    selectedTeamId: Int,
    teamViewModel: TeamViewModel,
    setBottomSheetOpen: () -> Unit,
) {
    val team = selectedTeamId.let { teamViewModel.getTeamById(it) }

    if (isOpen) {
        ModalBottomSheet(
            onDismissRequest = setBottomSheetOpen,
            dragHandle = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                ) {
                    ElevatedButton(
                        onClick = { setBottomSheetOpen() },
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.padding(end = 10.dp),
                        )
                        Text(text = "Home")
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(
                        text = "${team?.full_name} Games",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        ) {

            if (team != null) {
                GameBottomSheetContent(selectedTeam = team, teamViewModel = teamViewModel)
            }

        }
    }
}

@Composable
fun GameBottomSheetContent(selectedTeam: Team, teamViewModel: TeamViewModel) {
    // get the list of games for the selected team
    val games = remember(selectedTeam.id) {
        // runBlocking is used here because fetchGamesForTeam is a suspend function
        runBlocking { teamViewModel.fetchGamesForTeam(selectedTeam.id) }
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Home \n Team",
                style = MaterialTheme.typography.titleMedium

            )
            Text(
                text = "Home \n Score",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Visitor \n Team",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Visitor \n Score",
                style = MaterialTheme.typography.titleMedium
            )

        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.primary
        )
    }

    LazyColumn(content = {
        items(games) { game ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
             {
                 val homeTeamFullName: List<String> = game.home_team.full_name.split(" ")
                 val visitorTeamFullName: List<String> = game.visitor_team.full_name.split(" ")
                 Column(
                     modifier = Modifier
                         .width(85.dp)
                 ) {
                    Text(text = homeTeamFullName[0])
                    Text(text = homeTeamFullName[1])
                 }
                 Text(text = "${game.home_team_score}")
                 Column(
                     modifier = Modifier
                         .width(85.dp)
                 ) {
                    Text(text = visitorTeamFullName[0])
                    Text(text = visitorTeamFullName[1])
                 }
                 Text(text = "${game.visitor_team_score}")
            }
        }
    })
}