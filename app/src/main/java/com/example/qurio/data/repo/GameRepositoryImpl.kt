package com.example.qurio.data.repository

import com.example.qurio.data.response.toDomainList
import com.example.qurio.data.source.remote.QuizApiService
import com.example.qurio.domain.entity.Game
import com.example.qurio.domain.entity.GameCategory
import com.example.qurio.domain.entity.Question
import com.example.qurio.domain.repository.DifficultyLevel
import com.example.qurio.domain.repository.GameRepository

class GameRepositoryImpl(
    private val quizApiService: QuizApiService
) : GameRepository {
    override suspend fun getAllGameCategories(): List<GameCategory> {
        TODO("Not yet implemented")
    }

    override suspend fun getQuestionsByCategoryId(
        genreId: Int,
        numberOfQuestions: Int,
        difficulty: DifficultyLevel
    ): List<Question> {
        val quizResponseDto = quizApiService.getQuiz(
            amount = numberOfQuestions,
            category = genreId,
            difficulty = difficulty.name.lowercase()
        )

        return quizResponseDto.toDomainList()
    }

    override suspend fun getAllRecentGames(): List<Game> {
        TODO("Not yet implemented")
    }

    override suspend fun saveGame(game: Game) {
        TODO("Not yet implemented")
    }

    override suspend fun getSelectedDifficulty(): DifficultyLevel {
        TODO("Not yet implemented")
    }

    override suspend fun setDifficulty(difficulty: DifficultyLevel) {
        TODO("Not yet implemented")
    }
}