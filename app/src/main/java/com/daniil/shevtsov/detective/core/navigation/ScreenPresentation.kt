package com.daniil.shevtsov.detective.core.navigation

import com.daniil.shevtsov.detective.feature.coreshell.domain.AppState
import com.daniil.shevtsov.detective.feature.game.presentation.gamePresentation


fun screenPresentationFunctionalCore(
    state: AppState
): ScreenViewState {
    return when (state.currentScreen) {
        Screen.Main -> ScreenViewState.Main(gamePresentation(state.gameState))
        Screen.FinishedGame -> ScreenViewState.Main(gamePresentation(state.gameState))
    }
}
