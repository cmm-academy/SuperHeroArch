package com.mstudio.superheroarch.remotedatasource.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.remotedatasource.model.toEpisodeEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class EpisodeRemoteEntityMapperTest {

    @Test
    fun `given an episode remote entity, when mapped to episode entity, then has the correct values`() {
        val expected = RickAndMortyRepositoryInstruments.givenAnEpisodeRemoteEntity()
        val actual = expected.toEpisodeEntity()

        assertEquals(expected.episode, actual.episode)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.airDate, actual.airDate)
    }
}