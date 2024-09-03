package com.mstudio.superheroarch.usecase

import com.mstudio.superheroarch.presentation.model.CharacterData
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import com.mstudio.superheroarch.repository.model.toCharacterData

class GetFavCharactersUseCase(
    private val repository: RickAndMortyRepository
) {

    suspend fun getFavCharacters(): List<CharacterData> = repository.getFavCharacters().map { it.toCharacterData() }
}