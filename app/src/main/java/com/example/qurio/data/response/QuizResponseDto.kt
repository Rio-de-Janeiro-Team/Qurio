package com.example.qurio.data.response


import android.text.Html
import com.example.qurio.domain.entity.Question
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

fun QuizResponseDto.toDomainList(): List<Question> {
    return this.results.map { it.toDomain() }
}

fun QuestionResponseDto.toDomain(): Question {
    val decodedQuestion =
        Html.fromHtml(question, Html.FROM_HTML_MODE_LEGACY).toString()
    val decodedCorrectAnswer =
        Html.fromHtml(correctAnswer, Html.FROM_HTML_MODE_LEGACY).toString()
    val decodedIncorrectAnswers = incorrectAnswers.map {
        Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY).toString()
    }

    return Question(
        id = 0,
        question = decodedQuestion,
        correctAnswer = decodedCorrectAnswer,
        incorrectAnswers = decodedIncorrectAnswers
    )
}

