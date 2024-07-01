package com.mstudio.superheroarch

import java.io.Serializable

data class Character(
    val name: String,
    val status: String,
    val image: String,
    val origin: Ubication,
    val location: Ubication
) : Serializable

data class Ubication(
    val name: String,
    val url: String
) : Serializable

data class CharacterResponse(
    val results: List<Character>
)