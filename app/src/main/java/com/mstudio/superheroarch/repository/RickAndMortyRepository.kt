package com.mstudio.superheroarch.repository

import android.content.Context
import androidx.room.Room
import com.mstudio.superheroarch.RickAndMortyApplication
import com.mstudio.superheroarch.localdatasource.RickAndMortyDatabase
import com.mstudio.superheroarch.localdatasource.model.CharacterLocalEntity
import com.mstudio.superheroarch.localdatasource.model.toCharactersRemoteEntity
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApi
import com.mstudio.superheroarch.remotedatasource.model.CharactersRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.EpisodeRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.toCharacterLocalEntity

class RickAndMortyRepository(
    val context: Context = RickAndMortyApplication.instance,
    private val api: RickAndMortyApi
) {

    private fun database(context: Context) = Room.databaseBuilder(
        context,
        RickAndMortyDatabase::class.java,
        "rickandmorty_database"
    ).build()

    suspend fun getCharacters(): List<CharactersRemoteEntity>? {
        val charactersFromLocal = getCharactersFromLocal()
        if (charactersFromLocal.isEmpty()) {
            val response = api.getCharacters()
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
        val response = api.getSingleEpisode(id)
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            } ?: throw Exception(response.errorBody().toString())
        } else {
            throw Exception(response.errorBody().toString())
        }
    }

    private suspend fun getCharactersFromLocal(): List<CharacterLocalEntity> =
        database(context).characterDao().getCharacters() ?: emptyList()

    private suspend fun saveCharacters(characters: List<CharacterLocalEntity>) {
        database(context).characterDao().insertCharacters(characters)
    }
}