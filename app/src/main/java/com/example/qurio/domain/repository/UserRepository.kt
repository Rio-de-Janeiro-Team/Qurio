package com.example.qurio.domain.repository

import com.example.qurio.domain.entity.Achievement
import com.example.qurio.domain.entity.UserAvatar

interface UserRepository {
    suspend fun getAvatar(): UserAvatar
    fun setAvatar(avatar: UserAvatar)

    suspend fun getLives(): Int
    fun addLives(lives: Int)

    suspend fun getUserCoinsBalance(): Int
    fun changeCoinsBalance(coinChange: Int)

    suspend fun getAchievements(): List<Achievement>
    fun setAchievementStaus(achievementName: String, isAchieved: Boolean )

    suspend fun getStreak(): Int
    fun setStreak(streak: Int)
}