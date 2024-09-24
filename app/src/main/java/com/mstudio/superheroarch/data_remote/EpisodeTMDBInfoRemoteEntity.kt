package com.mstudio.superheroarch.data_remote

import com.mstudio.superheroarch.repository.EpisodeTMDBInfoEntity

data class EpisodeTMDBInfoRemoteEntity (
    val rating: Double,
    val imageUrl: String
)
fun EpisodeTMDBInfoRemoteEntity.mapToEntity() = EpisodeTMDBInfoEntity(
    rating = rating,
    imageUrl = imageUrl
)