package com.mstudio.superheroarch.remotedatasource.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.remotedatasource.model.toCharacterData
import com.mstudio.superheroarch.remotedatasource.model.toCharacterLocalEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterRemoteEntityMapperTest {

    @Test
    fun `given a character remote entity, when mapped to character local entity, then has the correct values`() {
        val expected = RickAndMortyRepositoryInstruments.givenACharacterRemoteEntity()
        val actual = expected.toCharacterLocalEntity()

        assertEquals(expected.id, actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.status, actual.status)
        assertEquals(expected.species, actual.species)
        assertEquals(expected.origin.name, actual.origin)
        assertEquals(expected.location.name, actual.location)
        assertEquals(expected.episode.first(), actual.episode)
        assertEquals(expected.image, actual.image)
    }

    @Test
    fun `given a character remote entity, when mapped to character data, then has the correct values`() {
        val expected = RickAndMortyRepositoryInstruments.givenACharacterRemoteEntity()
        val actual = expected.toCharacterData()

        assertEquals(expected.id, actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.status, actual.status)
        assertEquals(expected.species, actual.species)
        assertEquals(expected.origin.name, actual.origin)
        assertEquals(expected.location.name, actual.location)
        assertEquals(expected.episode.first().split("/").last(), actual.firstEpisode)
        assertEquals(expected.image, actual.image)
    }
}