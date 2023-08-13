package com.example.nbainfo.network

import com.example.nbainfo.models.GameResponse
import com.example.nbainfo.models.PlayerResponse
import com.example.nbainfo.models.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("teams")
    suspend fun getTeams(): TeamResponse

    @GET("games")
    suspend fun getGamesForTeam(@Query("team_ids[]") teamId: Int): GameResponse

    @GET("players")
    suspend fun getPlayers(): PlayerResponse
}