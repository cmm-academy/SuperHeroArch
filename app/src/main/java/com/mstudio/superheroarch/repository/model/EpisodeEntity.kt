package com.mstudio.superheroarch.repository.model

import com.mstudio.superheroarch.presentation.model.Episode

data class EpisodeEntity(
    val name: String,
    val airDate: String,
    val episode: String
)

fun EpisodeEntity.toEpisode(): Episode =
    Episode(
        name = name,
        airDate = airDate,
        episode = episode
    )