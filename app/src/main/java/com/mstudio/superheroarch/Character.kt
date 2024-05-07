package com.mstudio.superheroarch

import java.io.Serializable

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val species: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val episode: List<String>
) : Serializable

data class Location(
    val name: String
) : Serializable
