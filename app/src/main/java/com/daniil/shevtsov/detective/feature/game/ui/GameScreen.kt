package com.daniil.shevtsov.detective.feature.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daniil.shevtsov.detective.feature.game.domain.GameAction
import com.daniil.shevtsov.detective.feature.game.domain.SlotId
import com.daniil.shevtsov.detective.feature.game.domain.SlottableId
import com.daniil.shevtsov.detective.feature.game.presentation.DragTarget
import com.daniil.shevtsov.detective.feature.game.presentation.DropTarget
import com.daniil.shevtsov.detective.feature.game.presentation.FormLineModel
import com.daniil.shevtsov.detective.feature.game.presentation.FormSectionModel
import com.daniil.shevtsov.detective.feature.game.presentation.GameViewState
import com.daniil.shevtsov.detective.feature.game.presentation.LongPressDraggable
import com.daniil.shevtsov.detective.feature.game.presentation.MotiveModel
import com.daniil.shevtsov.detective.feature.game.presentation.SlotModel
import com.daniil.shevtsov.detective.feature.game.presentation.SlottableModel
import timber.log.Timber

typealias OnGameAction = (action: GameAction) -> Unit
typealias OnDrop = (slotId: SlotId, slottableId: SlottableId) -> Unit

data class TextModel(
    val tag: String,
    val value: String,
    val color: Color,
)

@Preview
@Composable
fun GameScreenPreview() {
    val textModels = listOf(
        TextModel(
            tag = "Name",
            value = "John Doe:",
            color = Color.White
        ),
        TextModel(
            tag = "Text",
            value = " I was with",
            color = Color.Black
        ),
        TextModel(
            tag = "Name2",
            value = " John Smith",
            color = Color.White
        ),
    )

    var clickedTags by remember { mutableStateOf(setOf<String>()) }
    val tnc = "Terms and Condition"
    val privacyPolicy = "Privacy policy"
    val annotatedString = buildAnnotatedString {
        textModels.forEach { textModel ->
            with(textModel) {
                val color = when {
                    clickedTags.contains(tag) -> Color.DarkGray
                    else -> color
                }
                withStyle(style = SpanStyle(color = color)) {
                    pushStringAnnotation(tag = tag, annotation = value)
                    append(value)
                }
            }
        }
//        append("I have read ")
//        withStyle(style = SpanStyle(color = Color.Red)) {
//            pushStringAnnotation(tag = tnc, annotation = tnc)
//            append(tnc)
//        }
//        append(" and ")
//        withStyle(style = SpanStyle(color = Color.Red)) {
//            pushStringAnnotation(tag = privacyPolicy, annotation = privacyPolicy)
//            append(privacyPolicy)
//        }
//        append(clickedText)
    }
    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.let { span ->
                println("Clicked on ${span.item}")
                clickedTags = clickedTags + span.tag
            }
    })
//    GameScreen(
//        state = GameViewState(
//            trayWords = listOf(
//                slottableModel("Jane Doe"),
//                slottableModel("stole"),
//                slottableModel("cheburek")
//            ),
//            sections = listOf(
//                oneElementSection(title = "When", value = "23-04-29"),
//                oneElementSection(title = "Where", value = "Apartment no. 34 of 246 Green Street"),
//                formSectionModel(
//                    title = "Who and What", lines = listOf(
//                        formLineModel(
//                            listOf(
//                                slotModel("John Doe"),
//                                slotModel("shot"),
//                                slotModel("John Smith"),
//                                slotText("with"),
//                                slotModel(".44 revolver"),
//                            )
//                        ),
//                        formLineModel(
//                            listOf(
//                                slotModel("John Doe"),
//                                slotModel("died"),
//                                slotText("of"),
//                                slotModel("gunshot wound"),
//                            )
//                        ),
//                        formLineModel(
//                            listOf(
//                                slotModel("John Doe"),
//                                slotModel("took"),
//                                slotModel("golden idol")
//                            )
//                        ),
//                    )
//                ),
//                oneLineSection(
//                    title = "Why", line = formLineModel(
//                        listOf(
//                            slotModel("John Smith"),
//                            slotModel("took"),
//                            slotModel("golden idol")
//                        )
//                    )
//                ),
//            )
//        ),
//        onAction = {}
//    )
}

@Composable
fun GameScreen(
    state: GameViewState,
    onAction: OnGameAction,
) {
    LongPressDraggable(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.background(Color.Gray).fillMaxSize()) {
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
    DragTarget(modifier = Modifier, dataToDrop = model.id.raw) {
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
            sections.forEach { section ->
                FormSection(
                    title = section.title,
                    lines = section.lines,
                    onDrop = onDrop
                )
            }
        }
    }
}

@Composable
fun FormSection(
    title: String,
    lines: List<FormLineModel>,
    onDrop: OnDrop,
) {
    if (lines.size > 1) {
        Column(
            modifier = Modifier.background(Color.Gray),
            verticalArrangement = spacedBy(4.dp)
        ) {
            Text(title)
            lines.forEach { line ->
                FormLine(line, onDrop)
            }
        }
    } else if (lines.size == 1) {
        Row(
            modifier = Modifier.background(Color.Gray),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = spacedBy(4.dp)
        ) {
            Text(title)
            FormLine(lines[0], onDrop)
        }
    }
}

@Composable
fun FormLine(
    line: FormLineModel,
    onDrop: OnDrop,
) {
    Row(
        modifier = Modifier.background(Color.Gray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = spacedBy(4.dp)
    ) {
        line.slots.forEach {
            Slot(it, onDrop)
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
        if (model !is SlotModel.Text && slottableId != null && isInBound) {
            Timber.d("before launched effect for ${model.id}")
            LaunchedEffect(slottableId, isInBound) {
                Timber.d("inside launched effect for ${model.id}")
                onDrop(model.id, SlottableId(slottableId))
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

            is SlotModel.Text -> {
                Text(text = model.text)
            }
        }
    }
}

private fun slottableModel(text: String) = SlottableModel(
    id = SlottableId(0L),
    text = text,
)

private fun oneElementLine(element: SlotModel) = formLineModel(elements = listOf(element))

private fun oneElementSection(title: String, value: String) = oneLineSection(
    title = title,
    line = oneElementLine(slotModel(value))
)

private fun oneLineSection(title: String, line: FormLineModel) = formSectionModel(
    title = title,
    lines = listOf(line)
)

private fun formLineModel(elements: List<SlotModel>) = FormLineModel(elements)

private fun formSectionModel(title: String, lines: List<FormLineModel> = emptyList()) =
    FormSectionModel(title = title, lines = lines)

private fun slotModel(text: String) = SlotModel.Set(
    id = SlotId(0L),
    value = SlottableModel(
        id = SlottableId(0L),
        text = text,
    )
)

private fun slotText(text: String) = SlotModel.Text(SlotId(0L), text)
