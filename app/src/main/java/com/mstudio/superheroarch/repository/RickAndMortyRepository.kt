package com.mstudio.superheroarch.repository

import com.mstudio.superheroarch.remotedatasource.api.RetrofitInstance
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApi
import com.mstudio.superheroarch.remotedatasource.model.EpisodeRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.RickAndMortyRemoteEntity
import retrofit2.Response

class RickAndMortyRepository {

    suspend fun getCharacters(): Response<RickAndMortyRemoteEntity> {
        return RetrofitInstance.retrofit().create(RickAndMortyApi::class.java).getCharacters()
    }

    suspend fun getSingleEpisode(id: Int): Response<EpisodeRemoteEntity> {
        return RetrofitInstance.retrofit().create(RickAndMortyApi::class.java).getSingleEpisode(id)
    }
}