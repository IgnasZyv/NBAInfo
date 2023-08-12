package com.example.nbainfo

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("teams")
    suspend fun getTeams(): TeamResponse

    @GET("games")
    suspend fun getGamesForTeam(@Query("team_ids[]") teamId: Int): GameResponse
}