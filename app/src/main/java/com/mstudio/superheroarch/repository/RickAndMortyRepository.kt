package com.mstudio.superheroarch.repository

import com.mstudio.superheroarch.localdatasource.RickAndMortyDatabase
import com.mstudio.superheroarch.localdatasource.model.CharacterLocalEntity
import com.mstudio.superheroarch.localdatasource.model.toCharactersEntity
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApi
import com.mstudio.superheroarch.remotedatasource.model.toCharacterEntity
import com.mstudio.superheroarch.remotedatasource.model.toEpisodeEntity
import com.mstudio.superheroarch.repository.model.CharacterEntity
import com.mstudio.superheroarch.repository.model.EpisodeEntity
import com.mstudio.superheroarch.repository.model.toCharacterLocalEntity

class RickAndMortyRepository(
    private val database: RickAndMortyDatabase,
    private val api: RickAndMortyApi
) {

    suspend fun getCharacters(): List<CharacterEntity> {
        val charactersFromLocal = getCharactersFromLocal()
        if (charactersFromLocal.isEmpty()) {
            val response = api.getCharacters()
            if (response.isSuccessful) {
                val remoteResponse = response.body()?.characters?.map { it.toCharacterEntity() } ?: emptyList()
                if (remoteResponse.isNotEmpty()) {
                    saveCharacters(remoteResponse.map { it.toCharacterLocalEntity() })
                }
                return remoteResponse
            } else {
                throw Exception(response.errorBody().toString())
            }
        } else {
            return charactersFromLocal.map { it.toCharactersEntity() }
        }
    }

    suspend fun getSingleEpisode(id: Int): EpisodeEntity {
        val response = api.getSingleEpisode(id)
        if (response.isSuccessful) {
            response.body()?.let {
                return it.toEpisodeEntity()
            } ?: throw Exception(response.errorBody().toString())
        } else {
            throw Exception(response.errorBody().toString())
        }
    }

    private suspend fun getCharactersFromLocal(): List<CharacterLocalEntity> =
        database.characterDao().getCharacters() ?: emptyList()

    private suspend fun saveCharacters(characters: List<CharacterLocalEntity>) {
        database.characterDao().insertCharacters(characters)
    }

    suspend fun setFavoriteCharacter(isFavorite: Boolean, id: Int) {
        database.characterDao().updateFavoriteCharacter(isFavorite, id)
    }
}