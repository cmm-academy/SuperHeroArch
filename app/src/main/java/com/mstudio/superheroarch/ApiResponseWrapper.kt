package com.mstudio.superheroarch

data class ApiResponseWrapper(
    val results: List<Character>
)

data class Episode(
    val name: String,
    val air_date: String,
    val episode: String
)