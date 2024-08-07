package com.mstudio.superheroarch.presentation.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.presentation.model.toCharacterAndEpisode
import com.mstudio.superheroarch.remotedatasource.model.toEpisode
import com.mstudio.superheroarch.remotedatasource.model.toTheMovieDbEpisode
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterDataMapperTest {

    @Test
    fun `given character data, when mapped to character and episode data, then return correct data`() {
        val characterData = RickAndMortyRepositoryInstruments.givenCharacterData()
        val episodeData = RickAndMortyRepositoryInstruments.givenAnEpisodeRemoteEntity().toEpisode()
        val episodeExtraData = RickAndMortyRepositoryInstruments.givenAnEpisodeExtraData().toTheMovieDbEpisode()
        val characterAndEpisodeData = characterData.toCharacterAndEpisode(episodeData, episodeExtraData)

        assertEquals(characterData.id, characterAndEpisodeData.id)
        assertEquals(characterData.name, characterAndEpisodeData.name)
        assertEquals(characterData.status, characterAndEpisodeData.status)
        assertEquals(characterData.species, characterAndEpisodeData.species)
        assertEquals(characterData.origin, characterAndEpisodeData.origin)
        assertEquals(characterData.location, characterAndEpisodeData.location)
        assertEquals(characterData.firstEpisode, characterAndEpisodeData.episodeData.episode)
        assertEquals(episodeData.name, characterAndEpisodeData.episodeData.name)
        assertEquals(episodeData.airDate, characterAndEpisodeData.episodeData.airDate)
        assertEquals(episodeExtraData.image, characterAndEpisodeData.episodeData.image)
        assertEquals(episodeExtraData.voteAverage, characterAndEpisodeData.episodeData.voteAverage)
    }
}