package com.mstudio.superheroarch.remotedatasource.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.remotedatasource.model.toTheMovieDbEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class TheMovieDbRemoteEntityMapperTest {

    @Test
    fun `given a the movie db remote entity, when mapped to the movie db episode entity, the has correct values`() {
        val expected = RickAndMortyRepositoryInstruments.givenAnEpisodeExtraDataRemoteEntity()
        val actual = expected.toTheMovieDbEntity()

        assertEquals("https://image.tmdb.org/t/p/w500${expected.image}", actual.image)
        assertEquals(expected.voteAverage.toString(), actual.voteAverage.toString())
    }
}