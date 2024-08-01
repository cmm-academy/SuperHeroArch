package com.mstudio.superheroarch.remotedatasource.model

import com.google.gson.annotations.SerializedName
import com.mstudio.superheroarch.presentation.model.TheMovieDbEpisode

data class TheMovieDbEpisodeRemoteEntity(
    @SerializedName("still_path") val image: String,
    @SerializedName("vote_average") val voteAverage: Double
)

fun TheMovieDbEpisodeRemoteEntity.toTheMovieDbEpisode(): TheMovieDbEpisode =
    TheMovieDbEpisode(
        image = image,
        voteAverage = voteAverage
    )