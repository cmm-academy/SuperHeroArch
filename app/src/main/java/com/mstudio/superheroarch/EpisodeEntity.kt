package com.mstudio.superheroarch

import java.io.Serializable

data class EpisodeEntity(
    val air_date: String,
    val episode: String
): Serializable

fun EpisodeEntity.mapToLocalEntity() = EpisodeEntity(
    air_date = air_date,
    episode = episode
)