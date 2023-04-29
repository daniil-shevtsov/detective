package com.daniil.shevtsov.detective.feature.game.presentation

import assertk.all
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import com.daniil.shevtsov.detective.feature.game.domain.gameState
import org.junit.jupiter.api.Test

internal class GamePresentationKtTest {
    @Test
    fun `should create initial state`() {
        val viewState = gamePresentation(
            state = gameState(
                perpetrator = "John Doe",
                victim = "John Smith",
                time = "23-04-29",
                place = "Apartment no. 34 of 246 Green Street",
                deathCause = "Gunshot Wound",
                weapon = ".44 revolver",
                murderAction = "shot",
                crimeAction = "took",
                stolenObject = "golden idol",
                motive = "took thee golden idol",
            )
        )
        assertThat(viewState)
            .all {
                prop(GameViewState::time).isEqualTo("23-04-29")
                prop(GameViewState::events).containsExactly(
                    "John Doe shot John Smith with .44 revolver",
                    "John Smith died of Gunshot Wound",
                    "John Doe took golden idol",
                )
                prop(GameViewState::place).isEqualTo("Apartment no. 34 of 246 Green Street")
                prop(GameViewState::motive).isEqualTo("John Smith took golden idol")
            }
    }
}
