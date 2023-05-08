package com.daniil.shevtsov.detective.core.navigation

import assertk.all
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import com.daniil.shevtsov.detective.feature.main.domain.AppState
import com.daniil.shevtsov.detective.feature.main.domain.appState
import org.junit.jupiter.api.Test

internal class GeneralFunctionalCoreTest {
    @Test
    fun `should replace current screen when opening another`() {
        val state = generalFunctionalCore(
            state = appState(currentScreen = Screen.Main),
            viewAction = GeneralViewAction.Open(screen = Screen.Conversation)
        )

        assertThat(state)
            .prop(AppState::currentScreen)
            .isEqualTo(Screen.Conversation)
    }

    @Test
    fun `should add screen to stack when opening it`() {
        val state = generalFunctionalCore(
            state = appState(currentScreen = Screen.Main, screenStack = listOf(Screen.Main)),
            viewAction = GeneralViewAction.Open(screen = Screen.Conversation)
        )

        assertThat(state)
            .prop(AppState::screenStack)
            .containsExactly(Screen.Main, Screen.Conversation)
    }

    @Test
    fun `should replace screen in stack if replace flag`() {
        val state = generalFunctionalCore(
            state = appState(currentScreen = Screen.Main, screenStack = listOf(Screen.Main)),
            viewAction = GeneralViewAction.Open(screen = Screen.Conversation, shouldReplace = true)
        )

        assertThat(state)
            .prop(AppState::screenStack)
            .containsExactly(Screen.Conversation)
    }

    @Test
    fun `should go back when back clicked`() {
        val state = generalFunctionalCore(
            state = appState(
                currentScreen = Screen.Conversation,
                screenStack = listOf(Screen.Main, Screen.Conversation)
            ),
            viewAction = GeneralViewAction.Back
        )

        assertThat(state)
            .prop(AppState::currentScreen)
            .isEqualTo(Screen.Main)
    }

    @Test
    fun `should remove last element from screen stack when back clicked`() {
        val state = generalFunctionalCore(
            state = appState(
                currentScreen = Screen.Conversation,
                screenStack = listOf(Screen.Main, Screen.Conversation)
            ),
            viewAction = GeneralViewAction.Back
        )

        assertThat(state)
            .prop(AppState::screenStack)
            .containsExactly(Screen.Main)
    }

    @Test
    fun `should do nothing on back click when only one screen in stack`() {
        val state = generalFunctionalCore(
            state = appState(
                currentScreen = Screen.Main,
                screenStack = listOf(Screen.Main)
            ),
            viewAction = GeneralViewAction.Back
        )

        assertThat(state)
            .all {
                prop(AppState::currentScreen).isEqualTo(Screen.Main)
                prop(AppState::screenStack).containsExactly(Screen.Main)
            }
    }

}
