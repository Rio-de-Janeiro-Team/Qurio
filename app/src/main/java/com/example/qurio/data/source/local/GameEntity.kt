package com.example.qurio.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "game")
data class GameEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val genre: String,
    val duration: String,
    val coinChange: Int,
    val stars: Int
)
