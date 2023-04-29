package com.daniil.shevtsov.detective.feature.main.domain

import com.daniil.shevtsov.detective.core.navigation.Screen
import com.daniil.shevtsov.detective.feature.game.domain.GameState
import com.daniil.shevtsov.detective.feature.game.domain.gameState


data class AppState(
    val currentScreen: Screen,
    val screenStack: List<Screen>,
    val gameState: GameState,
)

fun appState(
    currentScreen: Screen = Screen.Main,
    screenStack: List<Screen> = emptyList(),
    gameState: GameState = gameState(),
) = AppState(
    currentScreen = currentScreen,
    screenStack = screenStack,
    gameState = gameState,
)
