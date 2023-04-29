package com.daniil.shevtsov.detective.feature.game.domain

import com.daniil.shevtsov.detective.feature.main.domain.AppState

fun gameFunctionalCore(
    state: AppState,
    action: GameAction
): AppState = state
