package com.mstudio.superheroarch.remotedatasource.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.remotedatasource.model.toCharacterData
import com.mstudio.superheroarch.remotedatasource.model.toCharacterLocalEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterRemoteEntityMapperTest {

    @Test
    fun `given a character remote entity, when mapped to character local entity, then has the correct values`() {
        val remoteEntity = RickAndMortyRepositoryInstruments.givenACharacterRemoteEntity()
        val localEntity = remoteEntity.toCharacterLocalEntity()

        assertEquals(remoteEntity.id, localEntity.id)
        assertEquals(remoteEntity.name, localEntity.name)
        assertEquals(remoteEntity.status, localEntity.status)
        assertEquals(remoteEntity.species, localEntity.species)
        assertEquals(remoteEntity.origin.name, localEntity.origin)
        assertEquals(remoteEntity.location.name, localEntity.location)
        assertEquals(remoteEntity.episode.first(), localEntity.episode)
        assertEquals(remoteEntity.image, localEntity.image)
    }

    @Test
    fun `given a character remote entity, when mapped to character data, then has the correct values`() {
        val remoteEntity = RickAndMortyRepositoryInstruments.givenACharacterRemoteEntity()
        val characterData = remoteEntity.toCharacterData()

        assertEquals(remoteEntity.id, characterData.id)
        assertEquals(remoteEntity.name, characterData.name)
        assertEquals(remoteEntity.status, characterData.status)
        assertEquals(remoteEntity.species, characterData.species)
        assertEquals(remoteEntity.origin.name, characterData.origin)
        assertEquals(remoteEntity.location.name, characterData.location)
        assertEquals(remoteEntity.episode.first().split("/").last(), characterData.firstEpisode)
        assertEquals(remoteEntity.image, characterData.image)
    }
}