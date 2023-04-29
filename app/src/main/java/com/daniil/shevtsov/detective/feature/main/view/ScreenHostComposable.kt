package com.daniil.shevtsov.detective.feature.main.view

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.daniil.shevtsov.detective.core.navigation.GeneralViewAction
import com.daniil.shevtsov.detective.core.navigation.ScreenHostViewModel
import com.daniil.shevtsov.detective.core.navigation.ScreenViewAction
import com.daniil.shevtsov.detective.core.navigation.ScreenViewState
import com.daniil.shevtsov.detective.feature.game.ui.GameScreen


@Composable
fun ScreenHostComposable(
    viewModel: ScreenHostViewModel
) {
    val delegatedViewState by viewModel.state.collectAsState()

    BackHandler {
        viewModel.handleAction(ScreenViewAction.General(GeneralViewAction.Back))
    }

    when (val viewState = delegatedViewState) {
        is ScreenViewState.Main -> {
            GameScreen(
                state = viewState.state,
                onAction = { action -> viewModel.handleAction(ScreenViewAction.Game(action)) }
            )
        }
        ScreenViewState.Loading -> Unit
    }
}
