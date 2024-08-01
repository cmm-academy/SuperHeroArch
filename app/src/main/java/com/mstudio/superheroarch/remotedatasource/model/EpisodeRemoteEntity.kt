package com.mstudio.superheroarch.remotedatasource.model

import com.google.gson.annotations.SerializedName
import com.mstudio.superheroarch.presentation.model.Episode

data class EpisodeRemoteEntity(
    @SerializedName("name") val name: String,
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode") val episode: String
)

fun EpisodeRemoteEntity.toEpisode(): Episode =
    Episode(
        name = name,
        airDate = airDate,
        episode = episode
    )