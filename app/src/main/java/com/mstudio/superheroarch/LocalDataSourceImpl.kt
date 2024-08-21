package com.mstudio.superheroarch


class LocalDataSourceImpl(private val characterDao: CharacterDao) : CharacterLocalDataSource {
    override suspend fun getAllCharacters(): List<Character> {
        return characterDao.getAllCharacters()
    }

    override suspend fun insertAll(characters: List<Character>) {
        characterDao.insertAll(characters)
    }
}