package com.example.qurio.data.response


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class QuizResponseDto(
    @SerializedName("response_code") val responseCode: Int,
    @SerializedName("results") val results: List<QuestionResponseDto>
)

@Serializable
data class QuestionResponseDto(
    @SerializedName("type") val type: String,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("category") val category: String,
    @SerializedName("question") val question: String,
    @SerializedName("correct_answer") val correctAnswer: String,
    @SerializedName("incorrect_answers") val incorrectAnswers: List<String>
)

