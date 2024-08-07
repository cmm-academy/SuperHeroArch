package com.mstudio.superheroarch.remotedatasource.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.remotedatasource.model.toEpisode
import org.junit.Assert.assertEquals
import org.junit.Test

class EpisodeRemoteEntityMapperTest {

    @Test
    fun `given an episode remote entity, when mapped to episode, then has the correct values`() {
        val expected = RickAndMortyRepositoryInstruments.givenAnEpisodeRemoteEntity()
        val actual = expected.toEpisode()

        assertEquals(expected.episode, actual.episode)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.airDate, actual.airDate)
    }
}