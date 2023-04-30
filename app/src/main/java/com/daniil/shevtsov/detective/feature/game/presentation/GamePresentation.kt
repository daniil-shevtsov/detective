package com.daniil.shevtsov.detective.feature.game.presentation

import com.daniil.shevtsov.detective.feature.game.domain.FormElement
import com.daniil.shevtsov.detective.feature.game.domain.FormText
import com.daniil.shevtsov.detective.feature.game.domain.GameState
import com.daniil.shevtsov.detective.feature.game.domain.Slot

fun gamePresentation(state: GameState): GameViewState {
    if(state.slots.size < 6) {
        return GameViewState(
            time = SlotModel.Empty(0L),
            events = emptyList(),
            place = SlotModel.Empty(0L),
            motive = MotiveModel(
                subject = SlotModel.Empty(0L),
                verb = SlotModel.Empty(0L),
                objectNoun = SlotModel.Empty(0L),
            ),
            trayWords = emptyList(),
        )
    }

    return with(state) {
        GameViewState(
            time = mapSlot(slots[0].elements[0] as Slot),
            events = listOf(
                slots[2],
                slots[3],
                slots[4],
            ).map { line -> FormLineModel(line.elements.map { mapSlot(it) }) },
            place = mapSlot(slots[1].elements[0] as Slot),
            motive = MotiveModel(
                subject = mapSlot(slots[5].elements[0] as Slot),
                verb = mapSlot(slots[5].elements[1] as Slot),
                objectNoun = mapSlot(slots[5].elements[2] as Slot)
            ),
            trayWords = slottables.map { slottable ->
                with(slottable) {
                    SlottableModel(id = id, text = value)
                }
            }
        )
    }
}

private fun mapSlot(value: FormElement) = when (value) {
    is Slot -> when {
        value.content != null -> SlotModel.Set(
            value.id,
            SlottableModel(value.content.id, value.content.value)
        )
        else -> SlotModel.Empty(value.id)
    }
    is FormText -> SlotModel.Text(-12L, value.value)
}

