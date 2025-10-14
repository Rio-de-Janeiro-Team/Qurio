package com.example.qurio.data.response


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseDtoDto(
    @SerializedName("trivia_categories")
    val triviaCategories: List<TriviaCategoryDto>
)
@Serializable
data class TriviaCategoryDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)