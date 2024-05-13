package com.mstudio.superheroarch

class GetCharacterDetailsUseCase {

    private val repository = RickAndMortyRepository()
    private val tmdbRepository = TMDBRepository()

    suspend fun getCharacterDetails(character: Character): FullCharacterEntity? {
        try {
            val episodeNumber = character.episodes.first().substringAfterLast("/").toInt()
            val firstEpisodeDetails = repository.getEpisodeDetails(episodeNumber)
            val regex = Regex("S(\\d+)E(\\d+)")
            val matchResult = regex.find(firstEpisodeDetails.episodeNumber)
            val season = matchResult?.groups?.get(1)?.value?.toInt()
            val episode = matchResult?.groups?.get(2)?.value?.toInt()
            if (season == null || episode == null) {
                return null
            }
            val episodeRating = tmdbRepository.getEpisodeDetails(season, episode)
            return character.toCharacterUIEntity(firstEpisodeDetails, episodeRating)
        } catch (e: Exception) {
            return null
        }
    }

}