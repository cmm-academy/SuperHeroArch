package com.mstudio.superheroarch.repository

import java.io.Serializable

data class EpisodeEntity(
    val air_date: String,
    val episode: String
): Serializable