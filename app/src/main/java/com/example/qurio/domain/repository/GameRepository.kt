package com.example.qurio.domain.repository

import com.example.qurio.domain.entity.Game
import com.example.qurio.domain.entity.GameCategory
import com.example.qurio.domain.entity.Question

interface GameRepository {
    suspend fun getAllGameCategories(): List<GameCategory>
    suspend fun getQuestionsByCategoryId(genreId: Int, numberOfQuestions: Int): List<Question>
    suspend fun getAllRecentGames(): List<Game>
    suspend fun saveGame(game: Game)
    suspend fun getSelectedDifficulty(): DifficultyLevel
    suspend fun setDifficulty(difficulty: DifficultyLevel)
}

enum class DifficultyLevel(val level: String) {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard")
}