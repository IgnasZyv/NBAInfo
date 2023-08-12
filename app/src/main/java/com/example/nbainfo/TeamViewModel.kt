package com.example.nbainfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel() {
    private val _teams = MutableLiveData<List<Team>>()
    var teams : MutableLiveData<List<Team>> = _teams

    init {
        fetchTeams()
    }

    private fun fetchTeams() {
        // Make the api call in a coroutine
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.getTeams()
                _teams.value = response.data
            } catch (e: Exception) {
                Log.d("TeamViewModel", "fetchTeams: ${e.message}")
            }
        }
    }

    suspend fun fetchGamesForTeam(teamId: Int) : List<Game> {
        return try {
            val response = RetrofitInstance.apiService.getGamesForTeam(teamId)
            response.data
        } catch (e: Exception) {
            Log.d("TeamViewModel", "fetchGamesForTeam: ${e.message}")
            emptyList()
        }
    }

    fun getTeamById(id: Int) : Team? {
        return teams.value?.find { it.id == id }
    }
}