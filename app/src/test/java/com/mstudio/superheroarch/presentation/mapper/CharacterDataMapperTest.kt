package com.mstudio.superheroarch.presentation.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.presentation.model.toCharacterAndEpisode
import com.mstudio.superheroarch.repository.model.toEpisode
import com.mstudio.superheroarch.repository.model.toTheMovieDbEpisode
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterDataMapperTest {

    @Test
    fun `given character data, when mapped to character and episode data, then return correct data`() {
        val expected = RickAndMortyRepositoryInstruments.givenCharacterData()
        val expectedEpisodeData = RickAndMortyRepositoryInstruments.givenAnEpisodeEntity().toEpisode()
        val expectedEpisodeExtraData = RickAndMortyRepositoryInstruments.givenAnEpisodeExtraDataEntity().toTheMovieDbEpisode()
        val actual = expected.toCharacterAndEpisode(expectedEpisodeData, expectedEpisodeExtraData)

        assertEquals(expected.id, actual.id)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.status, actual.status)
        assertEquals(expected.species, actual.species)
        assertEquals(expected.origin, actual.origin)
        assertEquals(expected.location, actual.location)
        assertEquals(expected.firstEpisode, actual.episodeData.episode)
        assertEquals(expectedEpisodeData.name, actual.episodeData.name)
        assertEquals(expectedEpisodeData.airDate, actual.episodeData.airDate)
        assertEquals(expectedEpisodeExtraData.image, actual.episodeData.image)
        assertEquals(expectedEpisodeExtraData.voteAverage, actual.episodeData.voteAverage)
    }
}