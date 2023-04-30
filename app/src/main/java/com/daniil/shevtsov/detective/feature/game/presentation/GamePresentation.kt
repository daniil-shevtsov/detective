package com.daniil.shevtsov.detective.feature.game.presentation

import com.daniil.shevtsov.detective.feature.game.domain.FormElement
import com.daniil.shevtsov.detective.feature.game.domain.FormText
import com.daniil.shevtsov.detective.feature.game.domain.GameState
import com.daniil.shevtsov.detective.feature.game.domain.Slot

fun gamePresentation(state: GameState): GameViewState {
    if (state.slots.size < 3) {
        return GameViewState(
            trayWords = emptyList(),
            sections = emptyList(),
        )
    }

    return with(state) {
        GameViewState(
            trayWords = slottables.map { slottable ->
                with(slottable) {
                    SlottableModel(id = id, text = value)
                }
            },
            sections = state.formSections.map { formSection ->
                FormSectionModel(
                    title = formSection.title,
                    lines = formSection.formLines.map { formLine ->
                        FormLineModel(slots = formLine.elements.map { mapSlot(it) })
                    }
                )
            },
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

