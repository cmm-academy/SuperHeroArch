package com.mstudio.superheroarch

import android.database.Observable
import java.io.Serializable
import java.util.Objects

data class Character(
    val name: String,
    val status: String,
    val image: String,
    val origin: CharacterOrigin,
    val location: CharacterLocation
) : Serializable

data class CharacterLocation(
    val name: String,
    val url: String
) : Serializable

data class CharacterOrigin(
    val name: String,
    val url: String
) : Serializable

data class CharacterResponse(
    val results: List<Character>
)