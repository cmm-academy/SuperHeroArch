package com.mstudio.superheroarch.data_local

import com.mstudio.superheroarch.repository.CharacterEntity

interface CharacterLocalDataSource {
    suspend fun getAllCharacters(): List<CharacterLocalEntity>
    suspend fun insertAll(characters: List<CharacterEntity>)
}
