package com.daniil.shevtsov.detective.feature.game.domain

data class GameState(
    val slottables: List<Slottable>,
    val formSections: List<FormSection>,
    val history: History,
    val actors: Actors,
) {
    val allSlots: List<Slot>
        get() = formSections.flatMap { it.formLines.flatMap { it.elements } }
            .filterIsInstance<Slot>()
}

fun gameState(
    slottables: List<Slottable> = emptyList(),
    formSections: List<FormSection> = emptyList(),
    history: History = history(),
    actors: Actors = actors(),
) = GameState(
    slottables = slottables,
    formSections = formSections,
    history = history,
    actors = actors,
)
