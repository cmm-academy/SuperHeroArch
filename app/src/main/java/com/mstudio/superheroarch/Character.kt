package com.mstudio.superheroarch

data class Character(
    val name: String,
    val status: String,
    val image: String
)

data class CharacterResponse(
    val results: List<Character>
)