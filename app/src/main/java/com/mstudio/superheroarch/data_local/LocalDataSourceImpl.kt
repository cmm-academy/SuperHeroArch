package com.mstudio.superheroarch.data_local

import com.mstudio.superheroarch.data_local.CharacterDao
import com.mstudio.superheroarch.data_local.CharacterLocalDataSource
import com.mstudio.superheroarch.data_local.CharacterLocalEntity
import com.mstudio.superheroarch.repository.CharacterEntity
import com.mstudio.superheroarch.repository.mapToLocalEntityList


class LocalDataSourceImpl(private val characterDao: CharacterDao) : CharacterLocalDataSource {
    override suspend fun getAllCharacters(): List<CharacterLocalEntity> {
        return characterDao.getAllCharacters()
    }

    override suspend fun insertAll(characters: List<CharacterEntity>) {
        characterDao.insertAll(characters.mapToLocalEntityList())
    }
}