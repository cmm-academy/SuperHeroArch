package com.mstudio.superheroarch.localdatasource.mapper

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.localdatasource.model.toCharactersRemoteEntity
import org.junit.Assert
import org.junit.Test

class CharacterLocalEntityMapperTest {

    @Test
    fun `given a character local entity, when mapped to character remote entity, the has correct values`() {
        val localEntity = RickAndMortyRepositoryInstruments.givenACharacterLocalEntity()
        val remoteEntity = localEntity.toCharactersRemoteEntity()

        Assert.assertEquals(localEntity.id, remoteEntity.id)
        Assert.assertEquals(localEntity.name, remoteEntity.name)
        Assert.assertEquals(localEntity.status, remoteEntity.status)
        Assert.assertEquals(localEntity.species, remoteEntity.species)
        Assert.assertEquals(localEntity.origin, remoteEntity.origin.name)
        Assert.assertEquals(localEntity.location, remoteEntity.location.name)
        Assert.assertEquals(localEntity.episode, remoteEntity.episode.first())
        Assert.assertEquals(localEntity.image, remoteEntity.image)
    }
}