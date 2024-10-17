package com.mstudio.superheroarch.domain

import com.mstudio.superheroarch.presentation.EpisodeDetailsViewEntity
import com.mstudio.superheroarch.repository.EpisodeEntity

data class Episode(
    val airDate: String,
    val episode: String,
    val rating: Double,
    val imageUrl: String
)

fun EpisodeEntity.toDomainModel(rating: Double, imageUrl: String) = Episode(
    airDate = air_date,
    episode = episode,
    rating = rating,
    imageUrl = imageUrl
)

fun Episode.toViewEntity(): EpisodeDetailsViewEntity {
    return EpisodeDetailsViewEntity(
        airDate = this.airDate,
        episode = this.episode,
        rating = this.rating,
        imageUrl = this.imageUrl
    )
}

fun EpisodeEntity.toDomainModel() = Episode(
    airDate = air_date,
    episode = episode,
    rating = 0.0,
    imageUrl = ""
)