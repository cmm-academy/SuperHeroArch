package com.mstudio.superheroarch.usecase

import com.mstudio.superheroarch.repository.RickAndMortyRepository

class SetFavCharacterUseCase(private val repository: RickAndMortyRepository) {

    suspend fun setCharacterAsFav(isFav: Boolean, id: Int) {
        repository.setFavCharacter(isFav, id)
    }
}