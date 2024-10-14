package com.mstudio.superheroarch.presentation

data class EpisodeDetailsViewEntity(
    val air_date: String,
    val episode: String,
    val rating: Double,
    val imageUrl: String
)

fun EpisodeDetailsViewEntity.mapToViewEntity(
    air_date: String,
    episode: String,
    rating: Double,
    imageUrl: String
): EpisodeDetailsViewEntity {
    return EpisodeDetailsViewEntity(
        air_date = air_date,
        episode = episode,
        rating = rating,
        imageUrl = imageUrl
    )
}