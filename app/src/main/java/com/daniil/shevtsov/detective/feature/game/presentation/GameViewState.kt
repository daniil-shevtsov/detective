package com.daniil.shevtsov.detective.feature.game.presentation

sealed interface GameViewStateNew

data class GameViewState(
    val trayWords: List<SlottableModel>,
    val sections: List<FormSectionModel>,
) : GameViewStateNew


