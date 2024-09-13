package com.mstudio.superheroarch

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class RickAndMortyRepository(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterLocalDataSource
) {

    suspend fun fetchCharacters(): Result<List<CharacterEntity>> {
        try {
            val characters = remoteDataSource.getCharacters()
            localDataSource.insertAll(characters)
            return Result.success(characters)
        } catch (e: Exception) {
            return fetchCharactersFromLocal()
        }
    }

    private suspend fun fetchCharactersFromLocal(exception: Exception? = null): Result<List<CharacterEntity>> {
        val localCharacters = localDataSource.getAllCharacters()
        return if (localCharacters.isNotEmpty()) {
            Result.success(localCharacters.mapToEntityList())
        } else {
            Result.failure(exception ?: Exception("No characters found in local database"))
        }
    }

    suspend fun fetchEpisodeDetails(episodeUrl: String): Result<EpisodeEntity>{
        try {
            val response = remoteDataSource.getEpisode(episodeUrl)
            val episode = response.body()
            if (episode != null){
                return Result.success(episode)
            }else{
                return Result.failure(Exception("Error fetching episode details"))
            }
        }catch (e: IOException){
            return Result.failure(e)
        }
    }
}
