package com.mstudio.superheroarch.presentation.model

import java.io.Serializable

data class CharacterData(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val species: String,
    val origin: String,
    val location: String,
    val episodes: List<String>
) : Serializable