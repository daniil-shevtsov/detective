package com.daniil.shevtsov.detective.feature.game.presentation

import com.daniil.shevtsov.detective.feature.game.domain.*

fun gamePresentation(state: GameState): GameViewState {
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
    is FormText -> SlotModel.Text(SlotId(-12L), value.value)
}

