package com.mstudio.superheroarch

import java.io.IOException

class RickAndMortyRepository(private val apiRick: ApiRick, private val characterDao: CharacterDao) {

    suspend fun fetchCharacters(): Result<List<Character>> {
        return try {
            val response = apiRick.getCharacter()
            if (response.isSuccessful) {
                val characters = response.body()?.results ?: emptyList()
                characterDao.insertAll(*characters.toTypedArray())
                Result.success(characters)
            } else {
                Result.failure(Exception("Error fetching characters"))
            }
        } catch (e: Exception) {
            val localCharacters = characterDao.getAllCharacters()
            if (localCharacters.isNotEmpty()) {
                Result.success(localCharacters)
            } else {
                Result.failure(e)
            }
        }
    }

    suspend fun fetchEpisodeDetails(episodeUrl: String): Result<Episode> {
        return try {
            val response = apiRick.getEpisode(episodeUrl)
            if (response.isSuccessful) {
                val episode = response.body()
                if (episode != null) {
                    Result.success(episode)
                } else {
                    Result.failure(Exception("Episode not found"))
                }
            } else {
                Result.failure(Exception("Error fetching episode details"))
            }
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}