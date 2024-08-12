package com.mstudio.superheroarch.usecase

import com.mstudio.superheroarch.presentation.model.CharacterData
import com.mstudio.superheroarch.remotedatasource.model.toCharacterData
import com.mstudio.superheroarch.repository.RickAndMortyRepository

class GetAllCharactersUseCase(
    private val repository: RickAndMortyRepository
) {

    suspend fun getAllCharacters(): List<CharacterData>? {
        try {
            return repository.getCharacters()?.map { it.toCharacterData() }
        } catch (e: Exception) {
            return null
        }
    }
}