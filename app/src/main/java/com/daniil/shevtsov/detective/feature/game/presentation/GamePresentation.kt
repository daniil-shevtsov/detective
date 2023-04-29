package com.daniil.shevtsov.detective.feature.game.presentation

import com.daniil.shevtsov.detective.feature.game.domain.GameState

fun gamePresentation(state: GameState): GameViewState {
    return with(state) {
        GameViewState(
            time = time,
            events = listOf(
                "$perpetrator $murderAction $victim with $weapon",
                "$victim died of $deathCause",
                "$perpetrator took $stolenObject",
            ),
            place = place,
            motive = "$victim took $stolenObject",
        )
    }

}
