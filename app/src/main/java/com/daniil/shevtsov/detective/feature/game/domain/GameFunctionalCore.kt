package com.daniil.shevtsov.detective.feature.game.domain

import com.daniil.shevtsov.detective.feature.coreshell.domain.AppState

fun gameFunctionalCore(
    state: AppState,
    action: GameAction
): AppState = state
