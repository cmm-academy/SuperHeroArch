package com.mstudio.superheroarch.remotedatasource.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.remotedatasource.model.toEpisode
import org.junit.Assert.assertEquals
import org.junit.Test

class EpisodeRemoteEntityMapperTest {

    @Test
    fun `given an episode remote entity, when mapped to episode, then has the correct values`() {
        val remoteEntity = RickAndMortyRepositoryInstruments.givenAnEpisodeRemoteEntity()
        val episode = remoteEntity.toEpisode()

        assertEquals(remoteEntity.episode, episode.episode)
        assertEquals(remoteEntity.name, episode.name)
        assertEquals(remoteEntity.airDate, episode.airDate)
    }
}