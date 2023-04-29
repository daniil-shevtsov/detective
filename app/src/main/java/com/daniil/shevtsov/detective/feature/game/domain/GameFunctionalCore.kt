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
    return state
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
                listOf(Slot(id = 0L, content = null, type = SlottableType.Time)),
                listOf(Slot(id = 1L, content = null, type = SlottableType.Place)),
                listOf(
                    Slot(id = 2L, content = null, type = SlottableType.Person),
                    Slot(id = 3L, content = null, type = SlottableType.Verb),
                    Slot(id = 4L, content = null, type = SlottableType.Noun),
                ),
            )
        )
    )
}
