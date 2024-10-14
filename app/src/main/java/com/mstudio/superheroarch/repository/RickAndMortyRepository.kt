package com.mstudio.superheroarch.repository

import com.mstudio.superheroarch.data_local.CharacterLocalDataSource
import com.mstudio.superheroarch.data_local.mapToEntityList
import com.mstudio.superheroarch.data_remote.CharacterRemoteDataSource
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

    suspend fun fetchEpisodeDetails(episodeUrl: String): EpisodeEntity {
        return try {
            remoteDataSource.getEpisode(episodeUrl)
        } catch (e: IOException) {
            throw e
        }
    }
}
