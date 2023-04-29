package com.daniil.shevtsov.detective.feature.game.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.daniil.shevtsov.detective.feature.game.domain.GameAction
import com.daniil.shevtsov.detective.feature.game.domain.gameState
import com.daniil.shevtsov.detective.feature.game.presentation.GameViewState

typealias OnGameAction = (action: GameAction) -> Unit

@Preview
@Composable
fun GameScreenPreview() {
    GameScreen(
        state = GameViewState(
            domain = gameState(
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
            )
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
        with(state.domain) {
            Text("When: ${state.domain.time}")
            Text("Who and What:")
            Text("$perpetrator $murderAction $victim with $weapon")
            Text("$victim died of $deathCause")
            Text("$perpetrator took $stolenObject")
            Text("Where: $place")
            Text("Why: $victim took $stolenObject")
        }
    }
}
