package com.mstudio.superheroarch.data_remote

import com.mstudio.superheroarch.repository.EpisodeEntity

data class EpisodeRemoteEntity(
    val air_date: String,
    val episode: String
)

fun EpisodeRemoteEntity.mapToEntity() = EpisodeEntity(
    air_date = air_date,
    episode = episode
)

fun List<EpisodeRemoteEntity>.mapToEntityList() = map { it.mapToEntity() }
