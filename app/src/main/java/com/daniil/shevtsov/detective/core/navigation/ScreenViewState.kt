package com.daniil.shevtsov.detective.core.navigation

import com.daniil.shevtsov.detective.feature.game.presentation.GameViewState


sealed class ScreenViewState {
    object Loading : ScreenViewState()
    data class Main(val state: GameViewState) : ScreenViewState()
}
