package com.example.nbainfo

data class Team(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val full_name: String,
    val name: String
)

data class TeamResponse(
    val data: List<Team>,
    val meta: Meta
)

data class Meta(
    val totalPages: Int,
    val currentPage: Int,
    val nextPage: Int?,
    val perPage: Int,
    val totalCount: Int
)
