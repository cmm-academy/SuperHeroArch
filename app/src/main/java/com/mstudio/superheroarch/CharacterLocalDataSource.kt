package com.mstudio.superheroarch

interface CharacterLocalDataSource {
    suspend fun getAllCharacters(): List<CharacterLocalEntity>
    suspend fun insertAll(characters: List<CharacterEntity>)
}
