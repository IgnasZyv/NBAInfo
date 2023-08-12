package com.example.nbainfo

data class Game(
    val id: Int,
    val date: String,
    val home_team: Team,
    val home_team_score: Int,
    val period: Int,
    val postseason: Boolean,
    val season: Int,
    val status: String,
    val time: String,
    val visitor_team: Team,
    val visitor_team_score: Int
)

data class GameResponse(
    val data: List<Game>,
    val meta: Meta
)
