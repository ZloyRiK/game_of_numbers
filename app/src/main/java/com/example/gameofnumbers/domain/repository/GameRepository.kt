package com.example.gameofnumbers.domain.repository

import com.example.gameofnumbers.domain.entity.GameSettings
import com.example.gameofnumbers.domain.entity.Level
import com.example.gameofnumbers.domain.entity.Question

interface GameRepository {
    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question
    fun getSettings(level: Level): GameSettings
}