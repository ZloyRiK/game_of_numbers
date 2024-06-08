package com.example.gameofnumbers.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gameofnumbers.domain.entity.Level

class GameViewModelFactory(
    private val application: Application,
    private val level: Level
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass != GameViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        return GameViewModel(application, level) as T
    }
}