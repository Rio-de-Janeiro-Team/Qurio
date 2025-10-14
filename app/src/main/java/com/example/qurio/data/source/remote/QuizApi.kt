package com.example.qurio.data.source.remote

import com.example.qurio.data.response.QuizResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApi{
    @GET("api.php")
  suspend fun getQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: Int,
        @Query("difficulty") difficulty: String?,
    ): QuizResponseDto

 }
