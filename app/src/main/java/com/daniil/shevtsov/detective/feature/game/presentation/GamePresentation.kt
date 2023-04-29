package com.daniil.shevtsov.detective.feature.game.presentation

import com.daniil.shevtsov.detective.feature.game.domain.GameState
import com.daniil.shevtsov.detective.feature.game.domain.Slot

fun gamePresentation(state: GameState): GameViewState {
    return with(state) {
        GameViewState(
            time = if (slots.isNotEmpty()) {
                mapSlot(slots[0][0])
            } else {
                slotFromString(time)
            },
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
    value.isNotBlank() -> SlotModel.Set(0L, SlottableModel(0L, value))
    else -> SlotModel.Empty(0L)
}

private fun mapSlot(value: Slot) = when {
    value.content != null -> SlotModel.Set(
        value.id,
        SlottableModel(value.content.id, value.content.value)
    )
    else -> SlotModel.Empty(value.id)
}
