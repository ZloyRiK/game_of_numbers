package com.example.gameofnumbers.domain.usecases

import com.example.gameofnumbers.domain.entity.Question
import com.example.gameofnumbers.domain.repository.GameRepository

class GenerateQuestionUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(maxSumVale: Int): Question{
        return gameRepository.generateQuestion(maxSumVale, COUNT_OF_OPTIONS)
    }

    companion object{
        private const val COUNT_OF_OPTIONS = 6
    }
}