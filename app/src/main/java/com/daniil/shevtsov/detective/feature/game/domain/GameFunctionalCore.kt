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
    val initGameState = createInitialState()

    return state.copy(
        gameState = makeIdsUnique(initGameState)
    )


}

private fun makeSlotIdUnique(gameState: GameState): GameState {
    var idCounter = 0L
    return gameState.copy(formSections = gameState.formSections.mapIndexed { index, formSection ->
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
}

private fun makeIdsUnique(gameState: GameState): GameState {
    return makeSlotIdUnique(gameState)
//    return gameState.makeIdsUnique(
//        extractId = { property -> },
//        extractProperty = { main -> },
//        updateId = { updateId -> },
//        updateProperty =,
//    )
}

private fun <MainType, PropertyType, IdType> MainType.makeIdsUnique(
    extractId: (property: PropertyType) -> IdType,
    extractProperty: (main: MainType) -> List<PropertyType>,
    updateId: (property: PropertyType, newId: IdType) -> PropertyType,
    updateProperty: (main: MainType, new: List<PropertyType>) -> MainType,
    incrementId: (old: IdType) -> IdType,
): MainType {
    val oldProperties = extractProperty(this)
    var initialCounter = extractId(oldProperties.first())
    val newProperties = oldProperties.map { oldProperty ->
        val newProperty = updateId(oldProperty, initialCounter)
        initialCounter = incrementId(initialCounter)
        newProperty
    }
    return updateProperty(this, newProperties)
}

private fun createInitialState() = GameState(
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
            value = "John Doe",
            type = SlottableType.Person,
        ),
        slottable(
            value = "John Smith",
            type = SlottableType.Person,
        ),
        slottable(
            value = "23-04-29",
            type = SlottableType.Time,
        ),
        slottable(
            value = "Apartment no. 34 of 246 Green Street",
            type = SlottableType.Place,
        ),
        slottable(
            value = "Gunshot Wound",
            type = SlottableType.Noun,
        ),
        slottable(
            value = ".44 revolver",
            type = SlottableType.Noun,
        ),
        slottable(
            value = "shot",
            type = SlottableType.Verb,
        ),
        slottable(
            value = "took",
            type = SlottableType.Verb,
        ),
        slottable(
            value = "golden idol",
            type = SlottableType.Noun,
        ),
    ),
    history = History(events = emptyList()),
    actors = actors(
        actor(name = "John Doe"),
        actor(name = "John Smith"),
    ),
)

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
