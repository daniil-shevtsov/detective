package com.daniil.shevtsov.detective.feature.game.presentation

data class GameViewState(
    val time: Slot,
    val events: List<String>,
    val place: Slot,
    val motive: MotiveModel,
    val trayWords: List<String> = listOf("kek1", "kek2"),
)


