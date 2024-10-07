package com.mstudio.superheroarch.domain

import com.mstudio.superheroarch.repository.EpisodeEntity
import com.mstudio.superheroarch.repository.EpisodeTMDBInfoEntity

data class EpisodeDetailsEntity(
    val episodeEntity: EpisodeEntity,
    val tmdbInfo: EpisodeTMDBInfoEntity
)

fun EpisodeEntity.toDomain(tmdbInfo: EpisodeTMDBInfoEntity): EpisodeDetailsEntity {
    return EpisodeDetailsEntity(
        episodeEntity = this,
        tmdbInfo = tmdbInfo
    )
}
