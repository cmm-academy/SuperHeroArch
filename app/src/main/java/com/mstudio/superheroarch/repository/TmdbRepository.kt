package com.mstudio.superheroarch.repository

import com.mstudio.superheroarch.data_remote.TmdbRemoteDataSourceImpl
import com.mstudio.superheroarch.data_remote.mapToEntity

class TmdbRepository(private val tmdbRemoteDataSource: TmdbRemoteDataSourceImpl) {

    suspend fun getEpisodeTMDBInfo(seasonNumber: Int, episodeNumber: Int): EpisodeTMDBInfoEntity?{

        val remoteEntity = tmdbRemoteDataSource.getEpisodeDetails(seasonNumber, episodeNumber)
        return remoteEntity?.mapToEntity()
    }
}