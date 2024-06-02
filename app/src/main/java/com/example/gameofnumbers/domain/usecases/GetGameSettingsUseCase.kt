package com.example.gameofnumbers.domain.usecases

import com.example.gameofnumbers.domain.entity.GameSettings
import com.example.gameofnumbers.domain.entity.Level
import com.example.gameofnumbers.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val gameRepository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings{
        return gameRepository.getSettings(level)
    }
}