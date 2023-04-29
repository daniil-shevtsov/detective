package com.daniil.shevtsov.detective.feature.game.presentation

import com.daniil.shevtsov.detective.feature.game.domain.GameState

fun gamePresentation(state: GameState): GameViewState {
    return with(state) {
        GameViewState(
            time = slotFromString(time),
            events = when {
                state.perpetrator.isNotEmpty() -> listOf(
                    "$perpetrator $murderAction $victim with $weapon",
                    "$victim died of $deathCause",
                    "$perpetrator took $stolenObject",
                )
                else -> emptyList()
            },
            place = slotFromString(place),
            motive = MotiveModel(
                subject = slotFromString(victim),
                verb = slotFromString(crimeAction),
                objectNoun = slotFromString(stolenObject)
            ),
        )
    }
}

private fun slotFromString(value: String) = when {
    value.isNotBlank() -> Slot.Set(value)
    else -> Slot.Empty
}
