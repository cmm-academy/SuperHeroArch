package com.mstudio.superheroarch.repository

import com.mstudio.superheroarch.localdatasource.DataBaseInstance
import com.mstudio.superheroarch.localdatasource.model.CharacterLocalEntity
import com.mstudio.superheroarch.localdatasource.model.toCharactersRemoteEntity
import com.mstudio.superheroarch.remotedatasource.api.RetrofitInstance
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApi
import com.mstudio.superheroarch.remotedatasource.model.CharactersRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.EpisodeRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.toCharacterLocalEntity

class RickAndMortyRepository {

    suspend fun getCharacters(): List<CharactersRemoteEntity>? {
        val charactersFromLocal = getCharactersFromLocal()
        if (charactersFromLocal.isEmpty()) {
            val response = RetrofitInstance.retrofit().create(RickAndMortyApi::class.java).getCharacters()
            if (response.isSuccessful) {
                val remoteResponse = response.body()?.characters ?: emptyList()
                if (remoteResponse.isNotEmpty()) {
                    saveCharacters(remoteResponse.map { it.toCharacterLocalEntity() })
                }
                return response.body()?.characters
            } else {
                throw Exception(response.errorBody().toString())
            }
        } else {
            return charactersFromLocal.map { it.toCharactersRemoteEntity() }
        }
    }

    suspend fun getSingleEpisode(id: Int): EpisodeRemoteEntity {
        val response = RetrofitInstance.retrofit().create(RickAndMortyApi::class.java).getSingleEpisode(id)
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            } ?: throw Exception(response.errorBody().toString())
        } else {
            throw Exception(response.errorBody().toString())
        }
    }

    private fun getCharactersFromLocal(): List<CharacterLocalEntity> =
        DataBaseInstance.database.characterDao().getCharacters()

    private fun saveCharacters(characters: List<CharacterLocalEntity>) {
        DataBaseInstance.database.characterDao().insertCharacters(characters)
    }
}