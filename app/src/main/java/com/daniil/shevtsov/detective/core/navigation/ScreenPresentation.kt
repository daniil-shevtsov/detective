package com.daniil.shevtsov.detective.core.navigation

import com.daniil.shevtsov.detective.feature.game.domain.GameState
import com.daniil.shevtsov.detective.feature.game.presentation.gamePresentation


fun screenPresentationFunctionalCore(
    state: GameState
): ScreenViewState {
    return when (state.currentScreen) {
        Screen.Main -> ScreenViewState.Main(gamePresentation(state))
        Screen.Conversation -> ScreenViewState.Main(gamePresentation(state))
    }
}
