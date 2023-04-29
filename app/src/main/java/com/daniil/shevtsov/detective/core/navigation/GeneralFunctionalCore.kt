package com.daniil.shevtsov.detective.core.navigation

import com.daniil.shevtsov.detective.feature.coreshell.domain.AppState

fun generalFunctionalCore(
    state: AppState,
    viewAction: GeneralViewAction,
): AppState = when (viewAction) {
    is GeneralViewAction.Open -> {
        val newStack = when {
            viewAction.shouldReplace -> state.screenStack.dropLast(1) + listOf(viewAction.screen)
            else -> state.screenStack + listOf(viewAction.screen)
        }
        state.copy(
            currentScreen = viewAction.screen,
            screenStack = newStack,
        )
    }
    is GeneralViewAction.Back -> {
        if (state.screenStack.size > 1) {
            val newScreenStack = state.screenStack.dropLast(1)
            val newCurrentScreen = newScreenStack.last()
            state.copy(
                currentScreen = newCurrentScreen,
                screenStack = newScreenStack,
            )
        } else {
            state
        }
    }
}
