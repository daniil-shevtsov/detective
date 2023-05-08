package com.daniil.shevtsov.detective.feature.game.presentation

data class GameViewState(
    val trayWords: List<SlottableModel>,
    val sections: List<FormSectionModel>,
) : ContentViewState


