package com.example.qurio.domain.entity

data class Game (
    val id: Int,
    val date: String,
    val genre: GameCategory,
    val duration: String,
    val coinChange: Int,
    val stars: Int
)