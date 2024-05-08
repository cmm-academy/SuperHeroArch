package com.mstudio.superheroarch

import com.google.gson.annotations.SerializedName

data class ApiResponseWrapper(
    val results: List<Character>
)

data class Episode(
    @SerializedName("name")val name: String,
    @SerializedName("air_date") val releaseDate: String,
    @SerializedName("episode") val episodeNumber: String
)