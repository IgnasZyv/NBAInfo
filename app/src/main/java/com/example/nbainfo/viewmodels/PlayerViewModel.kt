package com.example.nbainfo.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbainfo.models.Player
import com.example.nbainfo.models.SearchCriteria
import com.example.nbainfo.network.RetrofitInstance
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {
    private val _players = MutableLiveData<List<Player>>()
    var players : MutableLiveData<List<Player>> = _players

    // LiveData for the search results so the player list doesn't get updated
    private val _searchedPlayers = MutableLiveData<List<Player>>()
    var searchedPlayers : LiveData<List<Player>> = _searchedPlayers

    // state for changing between the list of players and the search results
    var searchActive = mutableStateOf(false)

    init {
        fetchPlayers()
    }

    private fun fetchPlayers() {
        // Make the api call in a coroutine
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.getPlayers()
                _players.value = response.data
                Log.d("PlayerViewModel", "fetchPlayers: ${response.data}")
            } catch (e: Exception) {
                Log.d("PlayerViewModel", "fetchPlayers: ${e.message}")
            }
        }
    }

    fun searchForPlayers(searchText: String, selectedSearchCriteria: SearchCriteria) {
        // filter the players based on the selected search criteria
        val searchedPlayers = when (selectedSearchCriteria) {
            SearchCriteria.Name -> players.value?.filter { it.first_name.contains(searchText, ignoreCase = true)}
            SearchCriteria.LastName -> players.value?.filter { it.last_name.contains(searchText, ignoreCase = true) }
            SearchCriteria.Team -> players.value?.filter { it.team.full_name.contains(searchText, ignoreCase = true) }
        }
        // update the searchedPlayers LiveData
        _searchedPlayers.value = searchedPlayers
    }
}

