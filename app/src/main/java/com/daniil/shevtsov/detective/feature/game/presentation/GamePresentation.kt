package com.daniil.shevtsov.detective.feature.game.presentation

import com.daniil.shevtsov.detective.feature.game.domain.GameState
import com.daniil.shevtsov.detective.feature.game.domain.Slot

fun gamePresentation(state: GameState): GameViewState {
    return with(state) {
        GameViewState(
            time = mapSlot(slots[0].elements[0] as Slot),
            events = emptyList()/*listOf(
                FormLineModel(listOf())
            ),
            when {
                state.perpetrator.isNotEmpty() -> listOf(
                    "$perpetrator $murderAction $victim with $weapon",
                    "$victim died of $deathCause",
                    "$perpetrator took $stolenObject",
                )
                else -> emptyList()
            }*/,
            place = mapSlot(slots[1].elements[0] as Slot),
            motive = MotiveModel(
                subject = mapSlot(slots[2].elements[0] as Slot),
                verb = mapSlot(slots[2].elements[1] as Slot),
                objectNoun = mapSlot(slots[2].elements[2] as Slot)
            ),
            trayWords = slottables.map { slottable ->
                with(slottable) {
                    SlottableModel(id = id, text = value)
                }
            }
        )
    }
}

private fun mapSlot(value: Slot) = when {
    value.content != null -> SlotModel.Set(
        value.id,
        SlottableModel(value.content.id, value.content.value)
    )
    else -> SlotModel.Empty(value.id)
}
