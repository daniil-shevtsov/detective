package com.daniil.shevtsov.detective.feature.game.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.daniil.shevtsov.detective.feature.game.domain.GameAction
import com.daniil.shevtsov.detective.feature.game.presentation.GameViewState

typealias OnGameAction = (action: GameAction) -> Unit

@Preview
@Composable
fun GameScreenPreview() {
    GameScreen(
        state = GameViewState(
            time = "23-04-29",
            events = listOf(
                "John Doe shot John Smith with .44 revolver",
                "John Smith died of Gunshot Wound",
                "John Doe took golden idol",
            ),
            place = "Apartment no. 34 of 246 Green Street",
            motive = "John Smith took golden idol",
        ),
        onAction = {}
    )
}

@Composable
fun GameScreen(
    state: GameViewState,
    onAction: OnGameAction,
) {
    Column {
        with(state) {
            Text("When: $time")
            Text("Who and What:")
            state.events.forEach { event ->
                Text(event)
            }
            Text("Where: $place")
            Text("Why: $motive")
        }
    }
}
