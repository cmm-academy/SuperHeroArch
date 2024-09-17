package com.mstudio.superheroarch.domain

import com.mstudio.superheroarch.repository.CharacterEntity

class FilterCharactersByStatusUseCase {
    operator fun invoke(allCharacters: List<CharacterEntity>, status: String?): List<CharacterEntity> {
        return if (status == null) {
            allCharacters
        } else {
            allCharacters.filter { it.status.equals(status, ignoreCase = true) }
        }
    }
}