package com.example.nbainfo.views

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.example.nbainfo.models.Player
import com.example.nbainfo.viewmodels.PlayerViewModel

@Composable fun PlayerMainContent(playerViewModel: PlayerViewModel) {
    val players: List<Player> by playerViewModel.players.observeAsState(emptyList())

    Column {
        Header()
        ListPlayerTitles()
        ListPlayers(data = players) {

        }
    }
}

@Composable
fun Header() {
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
            modifier = Modifier.padding(10.dp),
        )
        ElevatedButton(onClick = { /*TODO*/ }) {
            Image(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)

            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "Search")
        }
    }
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
fun ListPlayers(data: List<Player>, onPlayerClick : (Player) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 0.dp, start = 15.dp, end = 15.dp)
    ) {
        items(data) { player ->
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

