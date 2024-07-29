package com.mstudio.superheroarch.remotedatasource.model

import com.google.gson.annotations.SerializedName

data class EpisodeRemoteEntity(
    @SerializedName("name") val name: String,
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode") val episode: String
)