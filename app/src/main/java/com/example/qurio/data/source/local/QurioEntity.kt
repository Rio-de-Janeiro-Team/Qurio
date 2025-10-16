package com.example.qurio.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qurio")
data class QurioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val key: String,
    val value: String
)
