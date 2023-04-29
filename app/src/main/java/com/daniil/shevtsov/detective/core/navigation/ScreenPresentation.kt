package com.daniil.shevtsov.detective.core.navigation

import com.daniil.shevtsov.detective.feature.game.presentation.gamePresentation
import com.daniil.shevtsov.detective.feature.main.domain.AppState


fun screenPresentationFunctionalCore(
    state: AppState
): ScreenViewState {
    return when (state.currentScreen) {
        Screen.Main -> ScreenViewState.Main(gamePresentation(state.gameState))
        Screen.FinishedGame -> ScreenViewState.Main(gamePresentation(state.gameState))
    }
}
