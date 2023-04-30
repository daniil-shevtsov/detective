package com.daniil.shevtsov.detective.feature.game.presentation

import com.daniil.shevtsov.detective.feature.game.domain.FormElement
import com.daniil.shevtsov.detective.feature.game.domain.FormText
import com.daniil.shevtsov.detective.feature.game.domain.GameState
import com.daniil.shevtsov.detective.feature.game.domain.Slot

fun gamePresentation(state: GameState): GameViewState {
    if (state.slots.size < 3) {
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
            sections = listOf(
                FormSectionModel(title = "When", lines = emptyList()),
                FormSectionModel(title = "Who and What", lines = emptyList()),
                FormSectionModel(title = "Where", lines = emptyList()),
                FormSectionModel(title = "Why", lines = emptyList()),
            ),
        )
    }

    return with(state) {
        GameViewState(
            time = mapSlot(slots[0].elements[0] as Slot),
            events = slots.drop(3).map { line -> FormLineModel(line.elements.map { mapSlot(it) }) },
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
            },
            sections = listOf(
                FormSectionModel(title = "When", lines = listOf(FormLineModel(listOf(mapSlot(slots[0].elements[0] as Slot))))),
                FormSectionModel(title = "Who and What", lines = emptyList()),
                FormSectionModel(title = "Where", lines = emptyList()),
                FormSectionModel(title = "Why", lines = emptyList()),
            ),
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

