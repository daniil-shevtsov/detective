package com.daniil.shevtsov.detective.feature.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daniil.shevtsov.detective.feature.game.domain.GameAction
import com.daniil.shevtsov.detective.feature.game.presentation.*
import timber.log.Timber

typealias OnGameAction = (action: GameAction) -> Unit
typealias OnDrop = (slotId: Long, slottableId: Long) -> Unit

@Preview
@Composable
fun GameScreenEmptyPreview() {
    GameScreen(
        state = GameViewState(
            time = emptySlotModel(),
            events = emptyList(),
            place = emptySlotModel(),
            motive = MotiveModel(
                subject = emptySlotModel(),
                verb = emptySlotModel(),
                objectNoun = emptySlotModel(),
            ),
            trayWords = emptyList()
        ),
        onAction = {}
    )
}

private fun slottableModel(text: String) = SlottableModel(
    id = 0L,
    text = text,
)

private fun emptySlotModel() = SlotModel.Empty(id = 0L)

private fun slotModel(text: String) = SlotModel.Set(
    id = 0L,
    value = SlottableModel(
        id = 0L,
        text = text,
    )
)

@Preview
@Composable
fun GameScreenPreview() {
    GameScreen(
        state = GameViewState(
            time = slotModel("23-04-29"),
            events = listOf(
                "John Doe shot John Smith with .44 revolver",
                "John Smith died of Gunshot Wound",
                "John Doe took golden idol",
            ),
            place = slotModel("Apartment no. 34 of 246 Green Street"),
            motive = MotiveModel(
                subject = slotModel("John Smith"),
                verb = slotModel("took"),
                objectNoun = slotModel("golden idol")
            ),
            trayWords = listOf(
                slottableModel("Jane Doe"),
                slottableModel("stole"),
                slottableModel("cheburek")
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
    LongPressDraggable(modifier = Modifier.size(600.dp)) {
        Column(modifier = Modifier.background(Color.Gray)) {
            FillInForm(
                state = state,
                onDrop = { slotId, slottableId ->
                    onAction(GameAction.SlottableDrop(slotId, slottableId))
                }
            )
            WordTray(state)
        }
    }
}

@Composable
fun WordTray(
    state: GameViewState
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
            .background(Color.DarkGray)
            .padding(4.dp),
        verticalArrangement = spacedBy(4.dp)
    ) {
        state.trayWords.forEach { word ->
            TrayWord(word)
        }
    }
}

@Composable
fun TrayWord(model: SlottableModel) {
    Timber.d("TrayWord with id ${model.id}")
    DragTarget(modifier = Modifier, dataToDrop = model.id) {
        Timber.d("Displaying $model with ${model.id} on drag")
        Text(
            modifier = Modifier.background(Color.Gray).padding(8.dp),
            text = model.text
        )
    }
}

@Composable
fun FillInForm(
    state: GameViewState,
    onDrop: OnDrop,
) {
    Column(modifier = Modifier.background(Color.Gray), verticalArrangement = spacedBy(4.dp)) {
        with(state) {
            SlotRow(title = "When", slot = state.time, onDrop = onDrop)
            Text("Who and What:")
            state.events.forEach { event ->
                Text(event)
            }
            SlotRow(title = "Where", slot = state.place, onDrop = onDrop)
            SlotRow(title = "Why") {
                Motive(motive, onDrop)
            }
        }
    }
}

@Composable
fun SlotRow(
    title: String,
    slot: SlotModel,
    onDrop: OnDrop,
) {
    SlotRow(title = title) {
        Slot(slot, onDrop)
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
fun Motive(
    model: MotiveModel,
    onDrop: OnDrop,
) {
    Row(
        modifier = Modifier.background(Color.Gray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = spacedBy(4.dp)
    ) {
        with(model) {
            Slot(model.subject, onDrop)
            Slot(model.verb, onDrop)
            Slot(model.objectNoun, onDrop)
        }
    }
}

@Composable
fun Slot(model: SlotModel, onDrop: OnDrop) {
    DropTarget<Long>(
        modifier = Modifier
    ) { isInBound, slottableId ->
        if (slottableId != null && isInBound) {
            Timber.d("before launched effect for ${model.id}")
            LaunchedEffect(slottableId, isInBound) {
                Timber.d("inside launched effect for ${model.id}")
                onDrop(model.id, slottableId)
            }
        }
        when (model) {
            is SlotModel.Empty -> Box(
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
            is SlotModel.Set -> Box(
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
                Text(textAlign = TextAlign.Center, text = model.value.text)
            }
        }
    }
}
