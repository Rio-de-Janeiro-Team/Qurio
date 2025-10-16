package com.example.qurio.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_character")
data class UserCharacterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val imageId: Int,
    val age: Int,
    val description: String,
    val price: Int
)
