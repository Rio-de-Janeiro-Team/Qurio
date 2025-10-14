package com.example.qurio.domain.entity

data class Achievement(
    val id: Int,
    val name: String,
    val description: String,
    val imageId: Int,
    val isAchieved: Boolean
)
