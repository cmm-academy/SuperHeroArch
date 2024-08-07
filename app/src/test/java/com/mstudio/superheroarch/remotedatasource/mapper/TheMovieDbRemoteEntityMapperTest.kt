package com.mstudio.superheroarch.remotedatasource.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.remotedatasource.model.toTheMovieDbEpisode
import org.junit.Assert.assertEquals
import org.junit.Test

class TheMovieDbRemoteEntityMapperTest {

    @Test
    fun `given a the movie db remote entity, when mapped to the movie db episode, the has correct values`() {
        val remoteEntity = RickAndMortyRepositoryInstruments.givenAnEpisodeExtraData()
        val theMovieDbEpisode = remoteEntity.toTheMovieDbEpisode()

        assertEquals(remoteEntity.image, theMovieDbEpisode.image)
        assertEquals(remoteEntity.voteAverage.toString(), theMovieDbEpisode.voteAverage.toString())
    }
}