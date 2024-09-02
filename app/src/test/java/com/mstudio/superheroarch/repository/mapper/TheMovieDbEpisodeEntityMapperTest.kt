package com.mstudio.superheroarch.repository.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.repository.model.toTheMovieDbEpisode
import org.junit.Assert.assertEquals
import org.junit.Test

class TheMovieDbEpisodeEntityMapperTest {
    @Test
    fun `given a the movie db entity, when mapped to the movie db episode, the has correct values`() {
        val expected = RickAndMortyRepositoryInstruments.givenAnEpisodeExtraDataEntity()
        val actual = expected.toTheMovieDbEpisode()

        assertEquals(expected.image, actual.image)
        assertEquals(expected.voteAverage.toString(), actual.voteAverage.toString())
    }
}