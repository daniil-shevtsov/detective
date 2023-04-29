package com.daniil.shevtsov.detective.feature.game.presentation

import com.daniil.shevtsov.detective.feature.game.domain.GameState

fun gamePresentation(state: GameState): GameViewState {
    return with(state) {
        GameViewState(
            time = Slot.Set(time),
            events = listOf(
                "$perpetrator $murderAction $victim with $weapon",
                "$victim died of $deathCause",
                "$perpetrator took $stolenObject",
            ),
            place = Slot.Set(place),
            motive = MotiveModel(
                subject = Slot.Set(victim),
                verb = Slot.Set(crimeAction),
                objectNoun = Slot.Set(stolenObject)
            ),
        )
    }

}
