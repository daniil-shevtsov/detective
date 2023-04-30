package com.daniil.shevtsov.detective.feature.game.domain

import com.daniil.shevtsov.detective.feature.main.domain.AppState

fun gameFunctionalCore(
    state: AppState,
    action: GameAction
): AppState {
    return when (action) {
        is GameAction.Init -> init(state)
        is GameAction.SlottableDrop -> onSlottableDrop(state, action)
    }
}

fun onSlottableDrop(state: AppState, action: GameAction.SlottableDrop): AppState {
    val slotOfDrop = state.gameState.slots.map { slotLists ->
        slotLists.elements.find { slot -> slot is Slot && slot.id == action.slotId }
    }.filterNotNull().firstOrNull() as? Slot
    val droppedSlottable =
        state.gameState.slottables.find { slottable -> slottable.id == action.slottableId }

    return if (slotOfDrop != null && droppedSlottable != null) {
        state.copy(
            gameState = state.gameState.copy(
                slottables = state.gameState.slottables/*.filter { slottable ->
                    slottable.id != droppedSlottable.id
                }*/,
                slots = state.gameState.slots.map { slotList ->
                    slotList.copy(
                        elements = slotList.elements.map { slot ->
                            when (slot) {
                                is Slot -> when (slot.id) {
                                    slotOfDrop.id -> slot.copy(content = droppedSlottable)
                                    else -> slot
                                }
                                else -> slot
                            }

                        }
                    )

                }
            )
        )
    } else {
        state
    }
}

private fun init(state: AppState): AppState {
    return state.copy(
        gameState = GameState(
            formSections = listOf(
                sectionWithEmptySlot(title = "When", type = SlottableType.Time),
                sectionWithEmptySlot(title = "Where", type = SlottableType.Place),
                sectionWithLines(
                    title = "Who and What",
                    formLine(
                        emptySlotOf(type = SlottableType.Person),
                        emptySlotOf(type = SlottableType.Verb),
                        emptySlotOf(SlottableType.Person),
                        FormText("with"),
                        emptySlotOf(type = SlottableType.Noun),
                    ),
                    formLine(
                        Slot(id = 9L, content = null, type = SlottableType.Person),
                        FormText("died of"),
                        Slot(id = 10L, content = null, type = SlottableType.Noun),
                    ),
                    formLine(
                        Slot(id = 11L, content = null, type = SlottableType.Person),
                        Slot(id = 12L, content = null, type = SlottableType.Verb),
                        Slot(id = 13L, content = null, type = SlottableType.Noun),
                    ),
                ),
                sectionWithOneLine(
                    title = "Why",
                    line = formLine(
                        emptySlotOf(type = SlottableType.Person),
                        emptySlotOf(type = SlottableType.Verb),
                        emptySlotOf(type = SlottableType.Noun),
                    )
                ),
            ),
            slottables = listOf(
                Slottable(
                    id = 0L,
                    value = "John Doe",
                    type = SlottableType.Person,
                ),
                Slottable(
                    id = 1L,
                    value = "John Smith",
                    type = SlottableType.Person,
                ),
                Slottable(
                    id = 2L,
                    value = "23-04-29",
                    type = SlottableType.Time,
                ),
                Slottable(
                    id = 3L,
                    value = "Apartment no. 34 of 246 Green Street",
                    type = SlottableType.Place,
                ),
                Slottable(
                    id = 4L,
                    value = "Gunshot Wound",
                    type = SlottableType.Noun,
                ),
                Slottable(
                    id = 5L,
                    value = ".44 revolver",
                    type = SlottableType.Noun,
                ),
                Slottable(
                    id = 6L,
                    value = "shot",
                    type = SlottableType.Verb,
                ),
                Slottable(
                    id = 7L,
                    value = "took",
                    type = SlottableType.Verb,
                ),
                Slottable(
                    id = 8L,
                    value = "golden idol",
                    type = SlottableType.Noun,
                ),
            ),
            slots = emptyList(),
        )
    )
}

fun formLine(vararg elements: FormElement) = FormLine(elements = elements.toList())

fun sectionWithOneLine(title: String, line: FormLine): FormSection = FormSection(
    title = title,
    formLines = listOf(line),
)

fun sectionWithLines(title: String, vararg lines: FormLine) = FormSection(
    title = title,
    formLines = lines.toList(),
)

private fun sectionWithEmptySlot(title: String, type: SlottableType) = sectionWithOneLine(
    title = title,
    line = FormLine(listOf(emptySlotOf(type)))
)

private fun lineWithEmptySlotOf(type: SlottableType) = FormLine(listOf(emptySlotOf(type)))
private fun emptySlotOf(type: SlottableType) = Slot(id = -1L, content = null, type = type)
