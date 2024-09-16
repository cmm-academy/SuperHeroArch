package com.mstudio.superheroarch.usecase

import com.mstudio.superheroarch.repository.RickAndMortyRepository

class UpdateFavoriteCharacterStatusUseCase(private val repository: RickAndMortyRepository) {

    suspend fun updateCharacterFavoriteStatus(isFavorite: Boolean, id: Int) {
        repository.updateFavoriteCharacterStatus(isFavorite, id)
    }
}