package com.example.qurio.data.source.remote

import com.example.qurio.data.response.CategoryResponseDtoDto
import com.example.qurio.data.response.QuizResponseDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuizApiService: QuizApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://opentdb.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val quizApi: QuizApi = retrofit.create(QuizApi::class.java)
   override suspend fun getQuiz(
        amount: Int,
        category: Int,
        difficulty: String?,
    ): QuizResponseDto {
        return quizApi.getQuiz(amount, category, difficulty)
    }

    override suspend fun getCategories(): CategoryResponseDtoDto {
        return quizApi.getCategories()
    }

}