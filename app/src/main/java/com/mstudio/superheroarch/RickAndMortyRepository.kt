package com.mstudio.superheroarch

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RickAndMortyRepository(private val apiRick: ApiRick) {

    suspend fun fetchCharacters(): Result<List<Character>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiRick.getCharacter()
                if (response.isSuccessful) {
                    val characters = response.body()?.results ?: emptyList()
                    Result.success(characters)
                } else {
                    Result.failure(Exception("Error fetching characters"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun fetchEpisodeDetails(episodeUrl: String): Result<Episode> {
        return withContext(Dispatchers.IO) {
            try {
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
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}