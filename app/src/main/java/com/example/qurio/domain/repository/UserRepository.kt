package com.example.qurio.domain.repository

import com.example.qurio.domain.entity.Achievement
import com.example.qurio.domain.entity.UserCharacter

interface UserRepository {
    suspend fun getCharacter(): UserCharacter
    suspend fun setCharacter(avatar: UserCharacter)

    suspend fun getLives(): Int
    suspend fun addLives(lives: Int)

    suspend fun getCoinsBalance(): Int
    suspend fun changeCoinsBalance(coinChange: Int)

    suspend fun getAchievements(): List<Achievement>
    suspend fun setAchievementStatus(achievementName: String, isAchieved: Boolean )

    suspend fun getStreak(): Int
    suspend fun setStreak(streak: Int)
}