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
            val episodeRating = tmdbRepository.getEpisodeDetails(season!!, episode!!)
            return FullCharacterEntity(
                id = character.id,
                name = character.name,
                status = character.status,
                image = character.image,
                species = character.species,
                gender = character.gender,
                origin = character.origin.name,
                location = character.location.name,
                firstEpisode = EpisodeUIEntity(
                    name = firstEpisodeDetails.name,
                    releaseDate = firstEpisodeDetails.releaseDate,
                    episodeNumber = firstEpisodeDetails.episodeNumber,
                    rating = episodeRating.rating,
                    imagePath = episodeRating.imagePath
                )
            )
        } catch (e: Exception) {
            return null
        }
    }

}