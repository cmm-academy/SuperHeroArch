package com.mstudio.superheroarch.presentation

data class EpisodeDetailsViewEntity(
    val airDate: String,
    val episode: String,
    val rating: Double,
    val imageUrl: String
)

fun EpisodeDetailsViewEntity.mapToViewEntity(
    airDate: String,
    episode: String,
    rating: Double,
    imageUrl: String
): EpisodeDetailsViewEntity {
    return EpisodeDetailsViewEntity(
        airDate = airDate,
        episode = episode,
        rating = rating,
        imageUrl = imageUrl
    )
}