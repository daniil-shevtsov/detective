package com.daniil.shevtsov.detective.feature.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daniil.shevtsov.detective.feature.game.domain.GameAction
import com.daniil.shevtsov.detective.feature.game.presentation.GameViewState
import com.daniil.shevtsov.detective.feature.game.presentation.Slot

typealias OnGameAction = (action: GameAction) -> Unit

@Preview
@Composable
fun GameScreenPreview() {
    GameScreen(
        state = GameViewState(
            time = Slot.Set("23-04-29"),
            events = listOf(
                "John Doe shot John Smith with .44 revolver",
                "John Smith died of Gunshot Wound",
                "John Doe took golden idol",
            ),
            place = Slot.Empty,
            motive = Slot.Set("John Smith took golden idol"),
        ),
        onAction = {}
    )
}

@Composable
fun GameScreen(
    state: GameViewState,
    onAction: OnGameAction,
) {
    Column(modifier = Modifier.background(Color.Gray)) {
        with(state) {
            SlotRow(title = "When", slot = state.time)

            Text("Who and What:")
            state.events.forEach { event ->
                Text(event)
            }
            SlotRow(title = "Where", slot = state.place)
            SlotRow(title = "Why", slot = state.motive)
        }
    }
}

@Composable
fun SlotRow(
    title: String,
    slot: Slot
) {
    Row(
        modifier = Modifier.background(Color.Gray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$title:")
        Slot(slot)
    }
}

@Composable
fun Slot(state: Slot) {
    when (state) {
        is Slot.Empty -> Box(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .background(Color.Gray)
                .padding(12.dp)
                .background(Color.Black)
        )
        is Slot.Set -> Box(
            modifier = Modifier
                .background(Color.Gray)
                .padding(4.dp)
                .background(Color.Black)
                .padding(4.dp)
                .background(Color.Gray)
                .padding(4.dp)
        ) {
            Text(state.value)
        }
    }
}
