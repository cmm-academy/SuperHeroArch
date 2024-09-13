package com.mstudio.superheroarch


class LocalDataSourceImpl(private val characterDao: CharacterDao) : CharacterLocalDataSource {
    override suspend fun getAllCharacters(): List<CharacterLocalEntity> {
        return characterDao.getAllCharacters()
    }

    override suspend fun insertAll(characters: List<CharacterEntity>) {
        characterDao.insertAll(characters.mapToLocalEntityList())
    }
}