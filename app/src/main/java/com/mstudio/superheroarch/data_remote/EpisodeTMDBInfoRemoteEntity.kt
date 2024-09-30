package com.mstudio.superheroarch.data_remote

import com.google.gson.annotations.SerializedName
import com.mstudio.superheroarch.repository.EpisodeTMDBInfoEntity

data class EpisodeTMDBInfoRemoteEntity (
    @SerializedName("vote_average") val rating: Double,
    @SerializedName("still_path") val imageUrl: String
)
fun EpisodeTMDBInfoRemoteEntity.mapToEntity() = EpisodeTMDBInfoEntity(
    rating = rating,
    imageUrl = imageUrl
)