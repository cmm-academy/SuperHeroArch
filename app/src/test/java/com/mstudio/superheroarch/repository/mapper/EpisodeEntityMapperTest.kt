package com.mstudio.superheroarch.repository.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.repository.model.toEpisode
import org.junit.Assert
import org.junit.Test

class EpisodeEntityMapperTest {

    @Test
    fun `given an episode entity, when mapped to episode, then has the correct values`() {
        val expected = RickAndMortyRepositoryInstruments.givenAnEpisodeEntity()
        val actual = expected.toEpisode()

        Assert.assertEquals(expected.episode, actual.episode)
        Assert.assertEquals(expected.name, actual.name)
        Assert.assertEquals(expected.airDate, actual.airDate)
    }
}