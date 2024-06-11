---
title: How to get all character information
---
# Introduction

This document will walk you through the implementation of the "Get All Character Information" feature. This feature allows us to fetch detailed information about a character from the Rick and Morty series, including their first episode details and ratings.

We will cover:

1. How we define the data classes for character and episode details.


2. How we convert a character to a full character entity.


3. How we fetch character details using a use case.


4. How we fetch episode details from two different repositories.

# Defining data classes for character and episode details

<SwmSnippet path="/app/src/main/java/com/mstudio/superheroarch/FullCharacterEntity.kt" line="3">

---

The first step in our implementation is defining the data classes that will hold the character and episode details. We have two data classes: FullCharacterEntity and FullEpisodeEntity. FullCharacterEntity holds the character's details, including their ID, name, status, image, species, gender, origin, location, and first episode details. The first episode details are stored in an instance of FullEpisodeEntity, which includes the episode's name, release date, episode number, rating, and image path.

```kotlin
data class FullCharacterEntity(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val species: String,
    val gender: String,
    val origin: String,
    val location: String,
    val firstEpisode: FullEpisodeEntity
)

data class FullEpisodeEntity(
    val name: String,
    val releaseDate: String,
    val episodeNumber: String,
    val rating: Double,
    val imagePath: String
)
```

---

</SwmSnippet>

# Converting a character to a full character entity

<SwmSnippet path="/app/src/main/java/com/mstudio/superheroarch/Character.kt" line="34">

---

Next, we have a function toFullCharacterEntity in the Character class that converts a character to a FullCharacterEntity. This function takes an Episode and TMDBEpisodeData as parameters, which are used to create a FullEpisodeEntity for the character's first episode.

```kotlin

fun Character.toFullCharacterEntity(episode: Episode, tmdbEpisodeData: TMDBEpisodeData) = FullCharacterEntity(
    id = id,
    name = name,
    status = status,
    image = image,
    species = species,
    gender = gender,
    origin = origin.name,
    location = location.name,
    firstEpisode = FullEpisodeEntity(
        name = episode.name,
        releaseDate = episode.releaseDate,
        episodeNumber = episode.episodeNumber,
        rating = tmdbEpisodeData.rating,
        imagePath = tmdbEpisodeData.imagePath
    )
)
```

---

</SwmSnippet>

# Fetching character details using a use case

<SwmSnippet path="/app/src/main/java/com/mstudio/superheroarch/GetCharacterDetailsUseCase.kt" line="3">

---

The GetCharacterDetailsUseCase class is responsible for fetching the details of a character. It uses the RickAndMortyRepository to fetch the first episode details of the character and the TMDBRepository to fetch the rating of the episode. The function getCharacterDetails in this class takes a Character as a parameter and returns a FullCharacterEntity if the episode details and rating are successfully fetched, otherwise it returns null.

```kotlin
class GetCharacterDetailsUseCase(
    private val repository: RickAndMortyRepository = RickAndMortyRepository(RickAndMortyApiFactory.create()),
    private val tmdbRepository: TMDBRepository = TMDBRepository()
) {

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
            return character.toFullCharacterEntity(firstEpisodeDetails, episodeRating)
        } catch (e: Exception) {
            return null
        }
    }

}
```

---

</SwmSnippet>

# Fetching episode details from repositories

<SwmSnippet path="/app/src/main/java/com/mstudio/superheroarch/RickAndMortyRepository.kt" line="33">

---

The getEpisodeDetails function in the RickAndMortyRepository class fetches the details of an episode from the Rick and Morty API. It takes an episode ID as a parameter and returns an Episode if the API call is successful, otherwise it throws an exception.

```kotlin
    suspend fun getEpisodeDetails(id: Int): Episode {
        val response = rickAndMortyApi.getEpisodeDetails(id)
        if (response.isSuccessful) {
            val episode = response.body()
            if (episode != null) {
                return episode
            } else {
                throw Exception("Episode not found")
            }
        } else {
            throw Exception(response.errorBody().toString())
        }
    }
```

---

</SwmSnippet>

<SwmSnippet path="/app/src/main/java/com/mstudio/superheroarch/TMDBRepository.kt" line="12">

---

Similarly, the getEpisodeDetails function in the TMDBRepository class fetches the details of an episode from the TMDB API. It takes the season and episode numbers as parameters and returns a TMDBEpisodeData if the API call is successful, otherwise it throws an exception.

```kotlin
    suspend fun getEpisodeDetails(season: Int, episode: Int): TMDBEpisodeData {
        val token = BuildConfig.TMDB_API_KEY
        val response = retrofit.create(TMDBApi::class.java).getEpisodeDetails("Bearer $token", season, episode)
        if (response.isSuccessful) {
            val remoteResponse = response.body()
            if (remoteResponse != null) {
                return remoteResponse.copy(imagePath = "$IMAGE_BASE_URL${remoteResponse.imagePath}")
            } else {
                throw Exception("Episode not found")
            }
        } else {
            throw Exception(response.errorBody().toString())
        }
    }
```

---

</SwmSnippet>

In conclusion, this feature fetches all the details of a character, including their first episode details and rating, by making use of two different APIs and converting the fetched data into a more convenient format.

<SwmMeta version="3.0.0" repo-id="Z2l0aHViJTNBJTNBU3VwZXJIZXJvQXJjaCUzQSUzQWNtbS1hY2FkZW15" repo-name="SuperHeroArch"><sup>Powered by [Swimm](https://app.swimm.io/)</sup></SwmMeta>
