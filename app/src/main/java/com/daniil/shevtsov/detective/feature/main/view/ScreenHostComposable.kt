package com.daniil.shevtsov.detective.feature.main.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.daniil.shevtsov.detective.core.navigation.GeneralViewAction
import com.daniil.shevtsov.detective.core.navigation.ScreenHostViewModel
import com.daniil.shevtsov.detective.core.navigation.ScreenViewAction
import com.daniil.shevtsov.detective.core.navigation.ScreenViewState
import com.daniil.shevtsov.detective.feature.conversation.ui.ConversationScreen
import com.daniil.shevtsov.detective.feature.game.domain.GameAction
import com.daniil.shevtsov.detective.feature.game.presentation.ContentViewState
import com.daniil.shevtsov.detective.feature.game.presentation.ConversationViewState
import com.daniil.shevtsov.detective.feature.game.presentation.GameViewState
import com.daniil.shevtsov.detective.feature.game.presentation.LongPressDraggable
import com.daniil.shevtsov.detective.feature.game.presentation.conversationViewStateCompose
import com.daniil.shevtsov.detective.feature.game.ui.OnGameAction
import com.daniil.shevtsov.detective.feature.game.ui.ThinkingScreen
import com.daniil.shevtsov.detective.feature.game.ui.gameViewStateCompose

@Preview(widthDp = 800)
@Composable
fun ScreenHostScreenPreview() {
    Row {
        ScreenHostScreen(
            state = ScreenViewState.Main(gameViewStateCompose()),
            modifier = Modifier.weight(1f),
            onAction = {}
        )
        ScreenHostScreen(
            state = ScreenViewState.Main(conversationViewStateCompose()),
            modifier = Modifier.weight(1f),
            onAction = {}
        )
    }
}

@Composable
fun ScreenHostComposable(
    viewModel: ScreenHostViewModel
) {
    val delegatedViewState by viewModel.state.collectAsState()

    ScreenHostScreen(
        state = delegatedViewState,
        onAction = { action -> viewModel.handleAction(action) }
    )
}

@Composable
fun ScreenHostScreen(
    state: ScreenViewState,
    onAction: (action: ScreenViewAction) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onAction(ScreenViewAction.General(GeneralViewAction.Back))
    }

    when (val viewState = state) {
        is ScreenViewState.Main -> {
            MainScreen(
                state = viewState,
                modifier = modifier,
                onAction = { action: GameAction -> onAction(ScreenViewAction.Game(action)) }
            )
        }

        ScreenViewState.Loading -> Unit
    }
}

@Composable
fun MainScreen(
    state: ScreenViewState.Main,
    onAction: OnGameAction,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row {
            Text("Thinking")
            Text("Conversation")
        }
        ScreenContent(
            state = state.content,
            onAction = onAction,
            modifier = modifier
        )
    }
}

@Composable
fun ScreenContent(
    state: ContentViewState,
    onAction: OnGameAction,
    modifier: Modifier = Modifier,
) {
    LongPressDraggable(modifier = modifier.fillMaxSize()) {
        when (state) {
            is ConversationViewState -> ConversationScreen(state, onAction)
            is GameViewState -> ThinkingScreen(state, onAction)
        }
    }
}
