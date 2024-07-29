package com.mstudio.superheroarch.repository

import com.mstudio.superheroarch.remotedatasource.api.RetrofitInstance
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApi
import com.mstudio.superheroarch.remotedatasource.model.EpisodeRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.RickAndMortyRemoteEntity

class RickAndMortyRepository {

    suspend fun getCharacters(): RickAndMortyRemoteEntity? {
        val response = RetrofitInstance.retrofit().create(RickAndMortyApi::class.java).getCharacters()
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception(response.errorBody().toString())
        }
    }

    suspend fun getSingleEpisode(id: Int): EpisodeRemoteEntity {
        val response = RetrofitInstance.retrofit().create(RickAndMortyApi::class.java).getSingleEpisode(id)
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            } ?: throw Exception(response.errorBody().toString())
        } else {
            throw Exception(response.errorBody().toString())
        }
    }
}