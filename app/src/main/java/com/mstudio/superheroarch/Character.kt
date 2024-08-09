package com.mstudio.superheroarch

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("characters")
data class Character(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val originName: String,
    val locationName: String,
    val firstEpisode: String
) : Serializable


data class Ubication(
    val name: String,
) : Serializable

data class Episode(
    val air_date: String,
    val episode: String
) : Serializable
