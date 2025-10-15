package com.example.qurio.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface QurioDao {
    @Insert
    suspend fun insertUserCharacter(userCharacter: UserCharacterEntity)
    @Query("SELECT * FROM user_character")
    suspend fun getUserCharacter(): UserCharacterEntity
    @Insert
    suspend fun insertGame(game: GameEntity)
    @Query("SELECT * FROM game")
    suspend fun getAllGames(): List<GameEntity>
    @Insert
    suspend fun insertQurio(qurio: QurioEntity)
    @Query("SELECT * FROM qurio")
    suspend fun getAllQurios(): List<QurioEntity>



}