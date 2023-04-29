package com.daniil.shevtsov.detective.feature.game.domain

import com.daniil.shevtsov.detective.feature.main.domain.AppState

fun gameFunctionalCore(
    state: AppState,
    action: GameAction
): AppState {
    return when (action) {
        GameAction.Init -> init(state)
    }
}

private fun init(state: AppState): AppState {
    return state.copy(
        gameState = GameState(
            perpetrator = "John Doe",
            victim = "John Smith",
            time = "23-04-29",
            place = "Apartment no. 34 of 246 Green Street",
            deathCause = "Gunshot Wound",
            weapon = ".44 revolver",
            murderAction = "shot",
            crimeAction = "took",
            stolenObject = "golden idol",
            motive = "took thee golden idol",
        )
    )
}
