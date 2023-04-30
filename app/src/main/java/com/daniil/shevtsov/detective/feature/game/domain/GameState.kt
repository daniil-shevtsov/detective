package com.daniil.shevtsov.detective.feature.game.domain

data class GameState(
    val slottables: List<Slottable>,
    val slots: List<FormLine>,
)

fun gameState(
    slottables: List<Slottable> = emptyList(),
    slots: List<FormLine> = listOf(FormLine(listOf(slot()))),
) = GameState(
    slottables = slottables,
    slots = slots,
)
