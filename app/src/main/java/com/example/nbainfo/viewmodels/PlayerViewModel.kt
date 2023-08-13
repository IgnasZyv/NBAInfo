package com.example.nbainfo.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbainfo.models.Player
import com.example.nbainfo.network.RetrofitInstance
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {
    private val _players = MutableLiveData<List<Player>>()
    var players : MutableLiveData<List<Player>> = _players

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
}