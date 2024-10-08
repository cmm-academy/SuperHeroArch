package com.mstudio.superheroarch.data_remote

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

object TmdbApiService {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

interface TmdbApi {
    @GET("tv/60625/season/{season_number}/episode/{episode_number}")
    suspend fun getEpisodeDetails(
        @Path("season_number") seasonNumber: Int,
        @Path("episode_number") episodeNumber: Int,
        @Header("Authorization") apiKey: String
    ): Response<EpisodeTMDBInfoRemoteEntity>
}
