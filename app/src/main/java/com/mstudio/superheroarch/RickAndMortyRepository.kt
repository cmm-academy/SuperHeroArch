package com.mstudio.superheroarch

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class RickAndMortyRepository(private val remoteDataSource: CharacterRemoteDataSource, private val localDataSource: CharacterLocalDataSource
) {

    suspend fun fetchCharacters(): Result<List<Character>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getCharacters()
                if (response.isSuccessful) {
                    val charactersDto = response.body()?.results ?: emptyList()
                    val characters = charactersDto.map { dto ->
                        val originName = dto.origin?.name ?: ""
                        val locationName = dto.location?.name ?: ""
                        val firstEpisode = dto.episode.firstOrNull() ?: ""

                        Character(
                            id = dto.id,
                            name = dto.name,
                            status = dto.status,
                            image = dto.image,
                            originName = originName,
                            locationName = locationName,
                            firstEpisode = firstEpisode
                        )
                    }

                    localDataSource.insertAll(characters)
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
        val localCharacters = localDataSource.getAllCharacters()
        return if (localCharacters.isNotEmpty()) {
            Result.success(localCharacters)
        } else {
            Result.failure(exception ?: Exception("No characters found in local database"))
        }
    }

    suspend fun fetchEpisodeDetails(episodeUrl: String): Result<Episode> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getEpisode(episodeUrl)
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
