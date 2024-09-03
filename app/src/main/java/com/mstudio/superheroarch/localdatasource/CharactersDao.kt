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

    @Query("SELECT * FROM CharacterLocalEntity WHERE isFav IS 1")
    suspend fun getFavouriteCharacters(): List<CharacterLocalEntity>

    @Query("UPDATE CharacterLocalEntity SET isFav = :isFav WHERE id = :id")
    suspend fun updateFavCharacter(isFav: Boolean, id: Int)
}