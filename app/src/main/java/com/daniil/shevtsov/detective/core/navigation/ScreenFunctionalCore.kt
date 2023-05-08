package com.daniil.shevtsov.detective.core.navigation

import com.daniil.shevtsov.detective.feature.game.domain.GameState
import com.daniil.shevtsov.detective.feature.game.domain.gameFunctionalCore


fun screenFunctionalCore(
    state: GameState,
    viewAction: ScreenViewAction,
): GameState {
    return when (viewAction) {
        is ScreenViewAction.General -> generalFunctionalCore(
            state = state,
            viewAction = viewAction.action,
        )

        is ScreenViewAction.Game -> gameFunctionalCore(
            state = state,
            action = viewAction.action
        )
    }
}
