package com.mstudio.superheroarch.remotedatasource.model

import com.google.gson.annotations.SerializedName

data class TheMovieDbEpisodeRemoteEntity(
    @SerializedName("still_path") val image: String,
    @SerializedName("vote_average") val voteAverage: Double
)