package com.daniil.shevtsov.detective.feature.game.presentation

data class GameViewState(
    val time: SlotModel,
    val events: List<String>,
    val place: SlotModel,
    val motive: MotiveModel,
    val trayWords: List<SlottableModel>,
)


