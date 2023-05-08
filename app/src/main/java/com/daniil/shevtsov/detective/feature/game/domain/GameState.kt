package com.daniil.shevtsov.detective.feature.game.domain

import com.daniil.shevtsov.detective.core.navigation.Screen

data class GameState(
    val slottables: List<Slottable>,
    val formSections: List<FormSection>,
    val history: History,
    val actors: Actors,
    val keyWords: List<KeyWord>,
    val currentScreen: Screen,
    val screenStack: List<Screen>,
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
    keyWords: List<KeyWord> = emptyList(),
    currentScreen: Screen = Screen.Main,
    screenStack: List<Screen> = emptyList(),
) = GameState(
    slottables = slottables,
    formSections = formSections,
    history = history,
    actors = actors,
    keyWords = keyWords,
    currentScreen = currentScreen,
    screenStack = screenStack,
)
