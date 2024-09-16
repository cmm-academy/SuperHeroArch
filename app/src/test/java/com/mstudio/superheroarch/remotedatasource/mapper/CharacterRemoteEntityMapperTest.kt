package com.mstudio.superheroarch.remotedatasource.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.remotedatasource.model.toCharacterEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class CharacterRemoteEntityMapperTest {

    @Test
    fun `given a character remote entity, when mapped to character entity, then has the correct values`() {
        val expected = RickAndMortyRepositoryInstruments.givenACharacterRemoteEntity()
        val actual = expected.toCharacterEntity()

        assertEquals(expected.id, actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.status, actual.status)
        assertEquals(expected.species, actual.species)
        assertEquals(expected.origin.name, actual.origin)
        assertEquals(expected.location.name, actual.location)
        assertEquals(expected.episode, actual.episode)
        assertEquals(expected.image, actual.image)
        assertFalse(actual.isFavorite)
    }
}