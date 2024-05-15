package com.mstudio.superheroarch.datacreation

import com.mstudio.superheroarch.Character
import com.mstudio.superheroarch.Location

object CharacterMockCreator {

    fun createCharacterMock(): Character {
        return Character(
            1,
            "Rick Sanchez",
            "Alive",
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            "Human",
            "Male",
            Location("Earth (C-137)"),
            Location("Earth (Replacement Dimension)"),
            listOf("https://rickandmortyapi.com/api/episode/1")
        )
    }

}