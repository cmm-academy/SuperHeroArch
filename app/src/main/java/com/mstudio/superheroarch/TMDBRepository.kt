package com.mstudio.superheroarch

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TMDBRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    suspend fun getEpisodeDetails(season: Int, episode: Int): TMDBEpisodeData {
        val token = BuildConfig.TMDB_API_KEY
        val response = retrofit.create(TMDBApi::class.java).getEpisodeDetails("Bearer $token", season, episode)
        if (response.isSuccessful) {
            val remoteResponse = response.body()
            if (remoteResponse != null) {
                return remoteResponse.copy(imagePath = "$IMAGE_BASE_URL${remoteResponse.imagePath}")
            } else {
                throw Exception("Episode not found")
            }
        } else {
            throw Exception(response.errorBody().toString())
        }
    }

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/tv/60625/"
        private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}