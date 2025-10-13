package com.example.qurio.domain.entity

data class Game (
    val date: String,
    val genre: GameGenre,
    val duration: String,
    val coinChange: Int,
    val stars: Int
)