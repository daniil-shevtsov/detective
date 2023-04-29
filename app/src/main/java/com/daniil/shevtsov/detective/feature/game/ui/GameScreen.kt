package com.daniil.shevtsov.detective.feature.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daniil.shevtsov.detective.feature.game.domain.GameAction
import com.daniil.shevtsov.detective.feature.game.presentation.*

typealias OnGameAction = (action: GameAction) -> Unit

@Preview
@Composable
fun GameScreenEmptyPreview() {
    GameScreen(
        state = GameViewState(
            time = Slot.Empty,
            events = emptyList(),
            place = Slot.Empty,
            motive = MotiveModel(
                subject = Slot.Empty,
                verb = Slot.Empty,
                objectNoun = Slot.Empty
            ),
            trayWords = listOf("lol", "kek", "cheburek")
        ),
        onAction = {}
    )
}

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
            place = Slot.Set("Apartment no. 34 of 246 Green Street"),
            motive = MotiveModel(
                subject = Slot.Set("John Smith"),
                verb = Slot.Set("took"),
                objectNoun = Slot.Set("golden idol")
            ),
        ),
        onAction = {}
    )
}

@Composable
fun GameScreen(
    state: GameViewState,
    onAction: OnGameAction,
) {
    LongPressDraggable(modifier = Modifier.size(600.dp)) {
        Column(modifier = Modifier.background(Color.Gray)) {
            FillInForm(state)
            WordTray(state)
        }
    }
}

@Composable
fun WordTray(
    state: GameViewState
) {
    Row(
        modifier = Modifier.padding(8.dp).background(Color.DarkGray).padding(4.dp),
        horizontalArrangement = spacedBy(4.dp)
    ) {
        state.trayWords.forEach { word->
            TrayWord(word)
        }
    }
}

@Composable
fun TrayWord(value: String) {
    DragTarget(modifier = Modifier, dataToDrop = value) {
        Text(
            modifier = Modifier.background(Color.Gray).padding(8.dp),
            text = value
        )
    }
}

@Composable
fun FillInForm(
    state: GameViewState,
) {
    Column(modifier = Modifier.background(Color.Gray), verticalArrangement = spacedBy(4.dp)) {
        with(state) {
            SlotRow(title = "When", slot = state.time)
            Text("Who and What:")
            state.events.forEach { event ->
                Text(event)
            }
            SlotRow(title = "Where", slot = state.place)
            SlotRow(title = "Why") {
                Motive(motive)
            }
        }
    }
}

@Composable
fun SlotRow(
    title: String,
    slot: Slot,
) {
    SlotRow(title = title) {
        DropTarget<String>(
            modifier = Modifier
        ) { isInBound, foodItem ->
//            if (foodItem != null && isInBound) {
//                Slot(Slot.Set(foodItem))
//            } else {
//                Slot.Empty
//            }
            Slot(slot)
        }
    }
}

@Composable
fun SlotRow(
    title: String,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier.background(Color.Gray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = spacedBy(4.dp)
    ) {
        Text(text = "$title:")
        content.invoke()
    }
}

@Composable
fun Motive(model: MotiveModel) {
    Row(
        modifier = Modifier.background(Color.Gray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = spacedBy(4.dp)
    ) {
        with(model) {
            Slot(model.subject)
            Slot(model.verb)
            Slot(model.objectNoun)
        }
    }
}

@Composable
fun Slot(state: Slot) {
    when (state) {
        is Slot.Empty -> Box(
            modifier = Modifier
                .width(80.dp)
                .background(Color.DarkGray)
                .padding(1.dp)
                .background(Color.Black)
                .padding(4.dp)
                .background(Color.Black)
                .padding(4.dp)
        ) {
            Text("")
        }
        is Slot.Set -> Box(
            modifier = Modifier
                .defaultMinSize(minWidth = 60.dp)
                .background(Color.DarkGray)
                .padding(1.dp)
                .background(Color.Black)
                .padding(4.dp)
                .background(Color.Gray)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(textAlign = TextAlign.Center, text = state.value)
        }
    }
}
