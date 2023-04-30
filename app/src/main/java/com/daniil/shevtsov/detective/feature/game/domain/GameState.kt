package com.daniil.shevtsov.detective.feature.game.domain

data class GameState(
    val slottables: List<Slottable>,
    val slots: List<FormLine>,
    val formSections: List<FormSection>,
)

fun gameState(
    slottables: List<Slottable> = emptyList(),
    slots: List<FormLine> = listOf(FormLine(listOf(slot()))),
    formSections: List<FormSection> = emptyList(),
) = GameState(
    slottables = slottables,
    slots = slots,
    formSections = formSections,
)
