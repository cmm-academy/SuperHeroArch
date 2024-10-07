package com.mstudio.superheroarch.presentation

import com.mstudio.superheroarch.repository.EpisodeEntity
import com.mstudio.superheroarch.repository.EpisodeTMDBInfoEntity

data class EpisodeDetailsViewEntity(
    val episodeEntity: EpisodeEntity,
    val tmdbInfo: EpisodeTMDBInfoEntity
)

fun mapToEpisodeDetailsViewEntity(
    episodeEntity: EpisodeEntity,
    tmdbInfo: EpisodeTMDBInfoEntity
): EpisodeDetailsViewEntity {
    return EpisodeDetailsViewEntity(
        episodeEntity = episodeEntity,
        tmdbInfo = tmdbInfo
    )
}