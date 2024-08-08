package com.mstudio.superheroarch

import java.io.IOException
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RickAndMortyRepository(private val apiRick: ApiRick, private val characterDao: CharacterDao) {

    suspend fun fetchCharacters(): Result<List<Character>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiRick.getCharacter()
                if (response.isSuccessful) {
                    val charactersDto = response.body()?.results ?: emptyList()
                    val characters = charactersDto.map { dto ->
                        Character(
                            name = dto.name,
                            status = dto.status,
                            image = dto.image,
                            originName = dto.origin?.name ?: "",
                            locationName = dto.location?.name ?: "",
                            firstEpisode = dto.episode.firstOrNull() ?: ""
                        )
                    }

                    characterDao.insertAll(characters)

                    Result.success(characters)
                } else {
                    Log.e("RickAndMortyRepository", "Error fetching characters: ${response.message()}")
                    fetchCharactersFromLocal()
                }
            } catch (e: Exception) {
                Log.e("RickAndMortyRepository", "Exception fetching characters", e)
                fetchCharactersFromLocal(e)
            }
        }
    }

    private suspend fun fetchCharactersFromLocal(exception: Exception? = null): Result<List<Character>> {
        val localCharacters = characterDao.getAllCharacters()
        return if (localCharacters.isNotEmpty()) {
            Result.success(localCharacters)
        } else {
            Result.failure(exception ?: Exception("No characters found in local database"))
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
            } catch (e: IOException) {
                Result.failure(e)
            }
        }
    }
}
