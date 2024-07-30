package com.mstudio.superheroarch

class RickAndMortyRepository(private val apiRick: ApiRick, private val characterDao: CharacterDao, private val episodeDao: EpisodeDao) {

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
                    episodeDao.insertEpisode(episode)
                    Result.success(episode)
                } else {
                    Result.failure(Exception("Episode not found"))
                }
            } else {
                Result.failure(Exception("Error fetching episode details"))
            }
        } catch (e: Exception) {
            val episodeCode = episodeUrl.split("/").last()
            val localEpisode = episodeDao.getEpisodeByCode(episodeCode)
            if (localEpisode != null) {
                Result.success(localEpisode)
            } else {
                Result.failure(e)
            }
        }
    }
    suspend fun getEpisodeFromDb(episodeCode: String): Episode? {
        return episodeDao.getEpisodeByCode(episodeCode)
    }
}