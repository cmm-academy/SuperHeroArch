package com.mstudio.superheroarch.localdatasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mstudio.superheroarch.localdatasource.model.CharacterLocalEntity

@Dao
interface CharactersDao {
    @Query("SELECT * FROM CharacterLocalEntity")
    suspend fun getCharacters(): List<CharacterLocalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterLocalEntity>)

    @Query("UPDATE CharacterLocalEntity SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteCharacter(isFavorite: Boolean, id: Int)
}