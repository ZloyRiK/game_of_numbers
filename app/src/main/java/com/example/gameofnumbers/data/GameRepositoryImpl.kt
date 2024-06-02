package com.example.gameofnumbers.data

import com.example.gameofnumbers.domain.entity.GameSettings
import com.example.gameofnumbers.domain.entity.Level
import com.example.gameofnumbers.domain.entity.Question
import com.example.gameofnumbers.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {
    private const val MIN_SUM = 2
    private const val MIN_ANSWER = 1
    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER, sum)
        val rightAnswer = sum - visibleNumber
        val options = HashSet<Int>()
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER)
        val to = min(maxSumValue, rightAnswer+countOfOptions)
        while (options.size<countOfOptions){
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getSettings(level: Level): GameSettings {
        return when(level){
            Level.TEST ->{
                GameSettings(
                    10,
                    3,
                    50,
                    8
                )
            }
            Level.EASY ->{
                GameSettings(
                    10,
                    10,
                    70,
                    60
                )
            }
            Level.NORMAL ->{
                GameSettings(
                    20,
                    20,
                    80,
                    40
                )
            }
            Level.HARD ->{
                GameSettings(
                    30,
                    30,
                    90,
                    40
                )
            }
        }
    }
}