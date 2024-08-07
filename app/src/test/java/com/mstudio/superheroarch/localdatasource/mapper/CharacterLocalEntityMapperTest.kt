package com.mstudio.superheroarch.localdatasource.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.localdatasource.model.toCharactersRemoteEntity
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterLocalEntityMapperTest {

    @Test
    fun `given a character local entity, when mapped to character remote entity, the has correct values`() {
        val expected = RickAndMortyRepositoryInstruments.givenACharacterLocalEntity()
        val actual = expected.toCharactersRemoteEntity()

        assertEquals(expected.id, actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.status, actual.status)
        assertEquals(expected.species, actual.species)
        assertEquals(expected.origin, actual.origin.name)
        assertEquals(expected.location, actual.location.name)
        assertEquals(expected.episode, actual.episode.first())
        assertEquals(expected.image, actual.image)
    }
}