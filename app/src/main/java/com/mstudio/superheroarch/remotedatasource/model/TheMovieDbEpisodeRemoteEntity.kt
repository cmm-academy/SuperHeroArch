package com.mstudio.superheroarch.remotedatasource.model

import com.google.gson.annotations.SerializedName
import com.mstudio.superheroarch.repository.model.TheMovieDbEpisodeEntity

private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

data class TheMovieDbEpisodeRemoteEntity(
    @SerializedName("still_path") val image: String,
    @SerializedName("vote_average") val voteAverage: Double
)

fun TheMovieDbEpisodeRemoteEntity.toTheMovieDbEntity(): TheMovieDbEpisodeEntity =
    TheMovieDbEpisodeEntity(
        image = "$BASE_IMAGE_URL$image",
        voteAverage = voteAverage
    )