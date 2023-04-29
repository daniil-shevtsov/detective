package com.daniil.shevtsov.detective.feature.game.domain

import com.daniil.shevtsov.detective.feature.main.domain.AppState
import timber.log.Timber

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

    Timber.d("need to update state: drop ${action.slottableId} into ${action.slotId}")
    return if (slotOfDrop != null && droppedSlottable != null) {
        Timber.d("found slot $slotOfDrop and want to drop $droppedSlottable")
        state.copy(
            gameState = state.gameState.copy(
                slottables = state.gameState.slottables.filter { slottable ->
                    slottable.id != droppedSlottable.id
                },
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
            perpetrator = "John Doe",
            victim = "John Smith",
            time = "23-04-29",
            place = "Apartment no. 34 of 246 Green Street",
            deathCause = "Gunshot Wound",
            weapon = ".44 revolver",
            murderAction = "shot",
            crimeAction = "took",
            stolenObject = "golden idol",
            motive = "took thee golden idol",
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
            slots = listOf(
                FormLine(listOf(Slot(id = 0L, content = null, type = SlottableType.Time))),
                FormLine(listOf(Slot(id = 1L, content = null, type = SlottableType.Place))),
                FormLine(
                    listOf(
                        Slot(id = 5L, content = null, type = SlottableType.Person),
                        Slot(id = 6L, content = null, type = SlottableType.Verb),
                        Slot(id = 7L, content = null, type = SlottableType.Person),
                        FormText("with"),
                        Slot(id = 8L, content = null, type = SlottableType.Noun),
                    )
                ),
                FormLine(
                    listOf(
                        Slot(id = 9L, content = null, type = SlottableType.Person),
                        FormText("died of"),
                        Slot(id = 10L, content = null, type = SlottableType.Noun),
                    )
                ),
                FormLine(
                    listOf(
                        Slot(id = 11L, content = null, type = SlottableType.Person),
                        Slot(id = 12L, content = null, type = SlottableType.Verb),
                        Slot(id = 13L, content = null, type = SlottableType.Noun),
                    )
                ),
                FormLine(
                    listOf(
                        Slot(id = 2L, content = null, type = SlottableType.Person),
                        Slot(id = 3L, content = null, type = SlottableType.Verb),
                        Slot(id = 4L, content = null, type = SlottableType.Noun),
                    )
                ),
            )
        )
    )
}
