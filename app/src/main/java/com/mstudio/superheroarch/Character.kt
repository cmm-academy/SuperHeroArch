package com.mstudio.superheroarch

import java.io.Serializable

data class Character(
    val name: String,
    val status: String,
    val image: String,
    val origin: Ubication,
    val location: Ubication,
    val episode: List<String>
) : Serializable

data class Ubication(
    val name: String,
    val url: String
) : Serializable

data class Episode(
    val air_date: String,
    val episode: String,
) : Serializable

data class CharacterResponse(
    val results: List<Character>
)