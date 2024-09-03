package com.mstudio.superheroarch.repository.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.repository.model.toCharacterData
import kotlin.math.exp
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterEntityMapperTest {

    @Test
    fun `given a character entity, when mapped to character data, then has the correct values`() {
        val expected = RickAndMortyRepositoryInstruments.givenACharacterEntity()
        val actual = expected.toCharacterData()

        assertEquals(expected.id, actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.status, actual.status)
        assertEquals(expected.species, actual.species)
        assertEquals(expected.origin, actual.origin)
        assertEquals(expected.location, actual.location)
        assertEquals(expected.episode.first().split("/").last(), actual.firstEpisode)
        assertEquals(expected.image, actual.image)
        assertEquals(expected.isFav, actual.isFav)
    }
}