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
    val slotOfDrop = state.gameState.allSlots.find { slot -> slot.id == action.slotId }
    val droppedSlottable =
        state.gameState.slottables.find { slottable -> slottable.id == action.slottableId }

    return if (slotOfDrop != null && droppedSlottable != null) {
        state.copy(
            gameState = state.gameState.copy(
                slottables = state.gameState.slottables,
                formSections = state.gameState.formSections.map { formSection ->
                    formSection.copy(
                        formLines = formSection.formLines.map { formLine ->
                            formLine.copy(elements = formLine.elements.map { element ->
                                when (element) {
                                    is Slot -> when (element.id) {
                                        slotOfDrop.id -> element.copy(content = droppedSlottable)
                                        else -> element
                                    }
                                    else -> element
                                }
                            })
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
    val initGameState = GameState(
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
                    emptySlotOf(type = SlottableType.Person),
                    FormText("died of"),
                    emptySlotOf(type = SlottableType.Noun),
                ),
                formLine(
                    emptySlotOf(type = SlottableType.Person),
                    emptySlotOf(type = SlottableType.Verb),
                    emptySlotOf(type = SlottableType.Noun),
                ),
            ),
            sectionWithLines(
                title = "Why",
                formLine(
                    emptySlotOf(type = SlottableType.Person),
                    emptySlotOf(type = SlottableType.Verb),
                    emptySlotOf(type = SlottableType.Noun),
                )
            ),
        ),
        slottables = listOf(
            slottable(
                id = 0L,
                value = "John Doe",
                type = SlottableType.Person,
            ),
            slottable(
                id = 1L,
                value = "John Smith",
                type = SlottableType.Person,
            ),
            slottable(
                id = 2L,
                value = "23-04-29",
                type = SlottableType.Time,
            ),
            slottable(
                id = 3L,
                value = "Apartment no. 34 of 246 Green Street",
                type = SlottableType.Place,
            ),
            slottable(
                id = 4L,
                value = "Gunshot Wound",
                type = SlottableType.Noun,
            ),
            slottable(
                id = 5L,
                value = ".44 revolver",
                type = SlottableType.Noun,
            ),
            slottable(
                id = 6L,
                value = "shot",
                type = SlottableType.Verb,
            ),
            slottable(
                id = 7L,
                value = "took",
                type = SlottableType.Verb,
            ),
            slottable(
                id = 8L,
                value = "golden idol",
                type = SlottableType.Noun,
            ),
        ),
        history = History(events = emptyList()),
        actors = actors(),
    )
    var idCounter = 0L
    return state.copy(
        gameState = initGameState.copy(formSections = initGameState.formSections.mapIndexed { index, formSection ->
            formSection.copy(
                formLines = formSection.formLines.map { formLine ->
                    formLine.copy(elements = formLine.elements.map { formElement ->
                        when (formElement) {
                            is Slot -> formElement.copy(
                                id = SlotId(idCounter++)
                            )
                            else -> formElement
                        }
                    })
                }
            )
        })
    )
}

fun formLine(vararg elements: FormElement) = FormLine(elements = elements.toList())

fun sectionWithLines(title: String, vararg lines: FormLine) = FormSection(
    title = title,
    formLines = lines.toList(),
)

fun sectionWithEmptySlot(title: String, type: SlottableType) = sectionWithLines(
    title = title,
    FormLine(listOf(emptySlotOf(type)))
)

fun emptySlotOf(type: SlottableType) = Slot(id = SlotId(-1L), content = null, type = type)
