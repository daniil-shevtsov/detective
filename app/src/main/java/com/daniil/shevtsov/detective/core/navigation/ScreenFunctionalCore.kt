package com.daniil.shevtsov.detective.core.navigation

import com.daniil.shevtsov.detective.feature.game.domain.gameFunctionalCore
import com.daniil.shevtsov.detective.feature.main.domain.AppState


fun screenFunctionalCore(
    state: AppState,
    viewAction: ScreenViewAction,
): AppState {
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
