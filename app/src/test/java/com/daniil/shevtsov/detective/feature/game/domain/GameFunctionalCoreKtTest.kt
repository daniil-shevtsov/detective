package com.daniil.shevtsov.detective.feature.game.domain

import assertk.all
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import com.daniil.shevtsov.detective.feature.main.domain.AppState
import com.daniil.shevtsov.detective.feature.main.domain.appState
import org.junit.jupiter.api.Test

internal class GameFunctionalCoreKtTest {
    @Test
    fun `should create npcs`() {
        val state = gameFunctionalCore(
            state = appState(
                gameState = gameState(

                )
            ),
            action = GameAction.Init
        )

        assertThat(state)
            .prop(AppState::gameState)
            .all {
                prop(GameState::perpetrator).isEqualTo("John Doe")
                prop(GameState::victim).isEqualTo("John Smith")
                prop(GameState::time).isEqualTo("23-04-29")
                prop(GameState::place).isEqualTo("Apartment no. 34 of 246 Green Street")
                prop(GameState::deathCause).isEqualTo("Gunshot Wound")
                prop(GameState::weapon).isEqualTo(".44 revolver")
                prop(GameState::murderAction).isEqualTo("shot")
                prop(GameState::crimeAction).isEqualTo("took")
                prop(GameState::stolenObject).isEqualTo("golden idol")
                prop(GameState::motive).isEqualTo("took thee golden idol")
            }
    }
}
