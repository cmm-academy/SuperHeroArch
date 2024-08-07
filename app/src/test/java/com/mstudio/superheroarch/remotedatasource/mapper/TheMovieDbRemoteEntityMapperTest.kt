package com.mstudio.superheroarch.remotedatasource.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.remotedatasource.model.toTheMovieDbEpisode
import org.junit.Assert.assertEquals
import org.junit.Test

class TheMovieDbRemoteEntityMapperTest {

    @Test
    fun `given a the movie db remote entity, when mapped to the movie db episode, the has correct values`() {
        val expected = RickAndMortyRepositoryInstruments.givenAnEpisodeExtraData()
        val actual = expected.toTheMovieDbEpisode()

        assertEquals(expected.image, actual.image)
        assertEquals(expected.voteAverage.toString(), actual.voteAverage.toString())
    }
}