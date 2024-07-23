package com.mstudio.superheroarch

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

object ApiService {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

interface ApiRick {
    @GET("character")
    suspend fun getCharacter(): Response<CharacterResponse>

    @GET
    suspend fun getEpisode(@Url episodeUrl: String): Response<Episode>
}