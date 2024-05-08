package com.mstudio.superheroarch

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RickAndMortyRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    suspend fun getCharacters() = retrofit.create(RickAndMortyApi::class.java).getCharacters()

    suspend fun getEpisodeDetails(id: Int) = retrofit.create(RickAndMortyApi::class.java).getEpisodeDetails(id)

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}