package com.mstudio.superheroarch

import android.content.Context
import androidx.room.Room

class RickAndMortyRepository(
    private val rickAndMortyApi: RickAndMortyApi,
    context: Context = RickAndMortyApplication.instance
) {

    private val database = Room.databaseBuilder(
        context,
        RickAndMortyDatabase::class.java,
        "rick_and_morty_database"
    ).build()

    suspend fun getCharacters(forceRefresh: Boolean): List<Character> {
        val localCharacters = getCharactersFromDatabase()
        if (localCharacters.isEmpty() || forceRefresh) {
            val remoteResponse = rickAndMortyApi.getCharacters()
            if (remoteResponse.isSuccessful) {
                val remoteCharacters = remoteResponse.body()?.results ?: emptyList()
                saveCharacters(remoteCharacters.toCharacterLocalEntityList())
                return remoteCharacters
            } else {
                throw Exception(remoteResponse.errorBody().toString())
            }
        } else {
            return localCharacters.toCharacterList()
        }
    }

    suspend fun getEpisodeDetails(id: Int): Episode {
        val response = rickAndMortyApi.getEpisodeDetails(id)
        if (response.isSuccessful) {
            val episode = response.body()
            if (episode != null) {
                return episode
            } else {
                throw Exception("Episode not found")
            }
        } else {
            throw Exception(response.errorBody().toString())
        }
    }

    private suspend fun saveCharacters(characters: List<CharacterLocalEntity>) {
        database.characterDao().insertAll(*characters.toTypedArray())
    }

    private suspend fun getCharactersFromDatabase() = database.characterDao().getAll()
}