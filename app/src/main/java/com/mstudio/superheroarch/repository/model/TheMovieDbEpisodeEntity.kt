package com.mstudio.superheroarch.repository.model

import com.mstudio.superheroarch.presentation.model.TheMovieDbEpisode

data class TheMovieDbEpisodeEntity(
    val image: String,
    val voteAverage: Double
)

fun TheMovieDbEpisodeEntity.toTheMovieDbEpisode(): TheMovieDbEpisode =
    TheMovieDbEpisode(
        image = image,
        voteAverage = voteAverage
    )