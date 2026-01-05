package com.mstudio.superheroarch

import java.io.Serializable

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
): Serializable

data class CharacterResponse(
    val results: List<Character>
)
