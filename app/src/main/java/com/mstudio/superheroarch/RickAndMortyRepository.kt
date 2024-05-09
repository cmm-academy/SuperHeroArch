package com.mstudio.superheroarch

import androidx.room.Room
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RickAndMortyRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val database = Room.databaseBuilder(
        RickAndMortyApplication.instance,
        RickAndMortyDatabase::class.java,
        "rick_and_morty_database"
    ).build()

    suspend fun getCharacters(forceRefresh: Boolean): List<Character> {
        val localCharacters = getCharactersFromDatabase()
        if (localCharacters.isEmpty() || forceRefresh) {
            val remoteResponse = retrofit.create(RickAndMortyApi::class.java).getCharacters()
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

    suspend fun getEpisodeDetails(id: Int) : Episode {
        val response = retrofit.create(RickAndMortyApi::class.java).getEpisodeDetails(id)
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

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}