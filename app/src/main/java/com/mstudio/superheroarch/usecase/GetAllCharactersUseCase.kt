package com.mstudio.superheroarch.usecase

import com.mstudio.superheroarch.presentation.model.CharacterData
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import com.mstudio.superheroarch.repository.model.toCharacterData

class GetAllCharactersUseCase(
    private val repository: RickAndMortyRepository
) {
    suspend fun getAllCharacters(): List<CharacterData> = repository.getCharacters().map { it.toCharacterData() }
}