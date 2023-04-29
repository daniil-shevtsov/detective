package com.daniil.shevtsov.detective.feature.game.presentation

import com.daniil.shevtsov.detective.feature.game.domain.GameState

fun gamePresentation(state: GameState): GameViewState {
    return GameViewState(
        domain = state
    )
}
