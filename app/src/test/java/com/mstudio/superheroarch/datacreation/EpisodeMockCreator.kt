package com.mstudio.superheroarch.datacreation

import com.mstudio.superheroarch.Episode
import com.mstudio.superheroarch.TMDBEpisodeData

object EpisodeMockCreator {

    fun createEpisodeMock(): Episode {
        return Episode(
            "Pilot",
            "December 2, 2014",
            "S01E01"
        )
    }

    fun createTMDBEpisodeDetailsMock(): TMDBEpisodeData {
        return TMDBEpisodeData(
            8.0,
            "imagePath"
        )
    }
}