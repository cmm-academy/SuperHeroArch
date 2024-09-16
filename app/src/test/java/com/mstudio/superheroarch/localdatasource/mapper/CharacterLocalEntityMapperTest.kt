package com.mstudio.superheroarch.localdatasource.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.localdatasource.model.toCharactersEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterLocalEntityMapperTest {

    @Test
    fun `given a character local entity, when mapped to character entity, the has correct values`() {
        val expected = RickAndMortyRepositoryInstruments.givenACharacterLocalEntity()
        val actual = expected.toCharactersEntity()

        assertEquals(expected.id, actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.status, actual.status)
        assertEquals(expected.species, actual.species)
        assertEquals(expected.origin, actual.origin)
        assertEquals(expected.location, actual.location)
        assertEquals(expected.episode, actual.episode.first())
        assertEquals(expected.image, actual.image)
        assertEquals(expected.isFavorite, actual.isFavorite)
    }
}