package com.mstudio.superheroarch.usecase

import com.mstudio.superheroarch.repository.RickAndMortyRepository

class SetFavoriteCharacterUseCase(private val repository: RickAndMortyRepository) {

    suspend fun setCharacterAsFavorite(isFavorite: Boolean, id: Int) {
        repository.setFavoriteCharacter(isFavorite, id)
    }
}