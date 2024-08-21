package com.mstudio.superheroarch

interface CharacterLocalDataSource {
    suspend fun getAllCharacters(): List<Character>
    suspend fun insertAll(characters: List<Character>)
}
