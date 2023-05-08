package com.daniil.shevtsov.detective.feature.main.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.daniil.shevtsov.detective.core.navigation.GeneralViewAction
import com.daniil.shevtsov.detective.core.navigation.ScreenHostViewModel
import com.daniil.shevtsov.detective.core.navigation.ScreenViewAction
import com.daniil.shevtsov.detective.core.navigation.ScreenViewState
import com.daniil.shevtsov.detective.feature.game.presentation.conversationViewStateCompose
import com.daniil.shevtsov.detective.feature.game.ui.GameScreen
import com.daniil.shevtsov.detective.feature.game.ui.gameViewStateCompose

@Preview
@Composable
fun ScreenHostScreenPreview() {
    Row {
        ScreenHostScreen(
            state = ScreenViewState.Main(gameViewStateCompose()),
            onAction = {}
        )
        ScreenHostScreen(
            state = ScreenViewState.Main(conversationViewStateCompose()),
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
    onAction: (action: ScreenViewAction) -> Unit
) {
    BackHandler {
        onAction(ScreenViewAction.General(GeneralViewAction.Back))
    }

    when (val viewState = state) {
        is ScreenViewState.Main -> {
            GameScreen(
                state = viewState.state,
                onAction = { action -> onAction(ScreenViewAction.Game(action)) }
            )
        }

        ScreenViewState.Loading -> Unit
    }
}
