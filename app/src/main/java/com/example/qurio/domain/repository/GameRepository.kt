package com.example.qurio.domain.repository

import com.example.qurio.domain.entity.Game
import com.example.qurio.domain.entity.GameGenre
import com.example.qurio.domain.entity.Question

interface GameRepository {
    suspend fun getAllGameGenres(): List<GameGenre>
    suspend fun getQuestionsByGenreName(genreName: String,difficulty: String,numberOfQuestions: Int): List<Question>
    suspend fun getAllRecentGames(): List<Game>
    fun saveGame(game: Game)
}