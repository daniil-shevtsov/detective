package com.daniil.shevtsov.detective.feature.game.presentation

import com.daniil.shevtsov.detective.feature.game.domain.GameState
import com.daniil.shevtsov.detective.feature.game.domain.Slot

fun gamePresentation(state: GameState): GameViewState {
    return with(state) {
        GameViewState(
            time = mapSlot(slots[0][0]),
            events = when {
                state.perpetrator.isNotEmpty() -> listOf(
                    "$perpetrator $murderAction $victim with $weapon",
                    "$victim died of $deathCause",
                    "$perpetrator took $stolenObject",
                )
                else -> emptyList()
            },
            place = mapSlot(slots[1][0]),
            motive = MotiveModel(
                subject = mapSlot(slots[2][0]),
                verb = mapSlot(slots[2][1]),
                objectNoun = mapSlot(slots[2][2])
            ),
            trayWords = slottables.map { slottable ->
                with(slottable) {
                    SlottableModel(id = id, text = value)
                }
            }
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
