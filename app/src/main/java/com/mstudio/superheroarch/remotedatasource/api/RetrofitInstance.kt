package com.mstudio.superheroarch.remotedatasource.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"
    private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/tv/60625/"

    fun retrofit(isMovieDbApi: Boolean = false): Retrofit {
        return Retrofit.Builder()
            .baseUrl(if (isMovieDbApi) TMDB_BASE_URL else BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}