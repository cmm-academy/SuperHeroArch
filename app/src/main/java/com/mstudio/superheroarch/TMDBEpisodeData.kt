package com.mstudio.superheroarch

import com.google.gson.annotations.SerializedName

data class TMDBEpisodeData(
    @SerializedName("vote_average") val rating: Double,
    @SerializedName("still_path") val imagePath: String,
)
