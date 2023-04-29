package com.daniil.shevtsov.detective.feature.game.ui

import androidx.compose.runtime.Composable
import com.daniil.shevtsov.detective.feature.game.domain.GameAction
import com.daniil.shevtsov.detective.feature.game.presentation.GameViewState

typealias OnGameAction = (action: GameAction) -> Unit

@Composable
fun GameScreen(
    state: GameViewState,
    onAction: OnGameAction,
) {

}
