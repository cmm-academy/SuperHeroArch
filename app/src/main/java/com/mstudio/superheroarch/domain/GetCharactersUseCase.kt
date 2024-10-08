package com.mstudio.superheroarch.domain

import com.mstudio.superheroarch.repository.CharacterEntity
import com.mstudio.superheroarch.repository.RickAndMortyRepository

class GetCharactersUseCase(private val repository: RickAndMortyRepository) {
    suspend operator fun invoke(): Result<List<CharacterEntity>> {
        return repository.fetchCharacters()
    }
}
