package com.example.nbainfo.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbainfo.models.SortCriteria
import com.example.nbainfo.models.Game
import com.example.nbainfo.models.Team
import com.example.nbainfo.network.RetrofitInstance
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel() {
    // LiveData for the list of teams
    private val _teams = MutableLiveData<List<Team>>()
    var teams : MutableLiveData<List<Team>> = _teams

    init {
        fetchTeams()
    }

    private fun fetchTeams() {
        // Make the api call in a coroutine
        viewModelScope.launch {
            try {
                // get the list of teams from the api
                val response = RetrofitInstance.apiService.getTeams()
                _teams.value = response.data
            } catch (e: Exception) {
                Log.e("TeamViewModel", "fetchTeams: ${e.message}")
            }
        }
    }

    suspend fun fetchGamesForTeam(teamId: Int) : List<Game> {
        return try {
            // get the list of games for the team from the api using the teamId
            val response = RetrofitInstance.apiService.getGamesForTeam(teamId)
            response.data
        } catch (e: Exception) {
            Log.e("TeamViewModel", "fetchGamesForTeam: ${e.message}")
            emptyList()
        }
    }

    fun getTeamById(id: Int) : Team? {
        return teams.value?.find { it.id == id }
    }

    fun sortTeamsBy(criteria: SortCriteria) {
        // sort the teams based on the selected criteria
        val sortedTeams = when (criteria) {
            SortCriteria.Name -> teams.value?.sortedBy { it.full_name }
            SortCriteria.City -> teams.value?.sortedBy { it.city }
            SortCriteria.Conference -> teams.value?.sortedBy { it.conference }
        }
        // update the teams LiveData
        _teams.value = sortedTeams
    }
}