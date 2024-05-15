package com.mstudio.superheroarch

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(): Response<ApiResponseWrapper>

    @GET("episode/{id}")
    suspend fun getEpisodeDetails(@Path("id") id: Int): Response<Episode>
}

object RickAndMortyApiFactory {
    fun create(): RickAndMortyApi {
        return try {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RickAndMortyApi::class.java)
        } catch (e: Exception) {
            throw RuntimeException("Failed to create RickAndMortyApi", e)
        }
    }
    private const val BASE_URL = "https://rickandmortyapi.com/api/"
}