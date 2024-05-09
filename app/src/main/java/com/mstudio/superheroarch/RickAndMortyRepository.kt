package com.mstudio.superheroarch

import androidx.room.Room
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RickAndMortyRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val database = Room.databaseBuilder(
        RickAndMortyApplication.instance,
        RickAndMortyDatabase::class.java,
        "rick_and_morty_database"
    ).build()

    suspend fun getCharacters() = retrofit.create(RickAndMortyApi::class.java).getCharacters()

    suspend fun getEpisodeDetails(id: Int) = retrofit.create(RickAndMortyApi::class.java).getEpisodeDetails(id)

    suspend fun saveCharacters(characters: List<CharacterLocalEntity>) {
        database.characterDao().insertAll(*characters.toTypedArray())
    }

    suspend fun getCharactersFromDatabase() = database.characterDao().getAll()

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}