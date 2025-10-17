package com.example.qurio.domain.entity

data class GameCategory(
    val id: Int,
    val name: String,
    val imageId: Int,
    val overlayColorResId: Int, // أو String لـ Hex Color
    val strokeColorResId: Int
)
