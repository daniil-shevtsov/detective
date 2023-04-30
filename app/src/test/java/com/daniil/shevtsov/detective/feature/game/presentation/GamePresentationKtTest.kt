package com.daniil.shevtsov.detective.feature.game.presentation

import assertk.Assert
import assertk.all
import assertk.assertThat
import assertk.assertions.*
import com.daniil.shevtsov.detective.feature.game.domain.SlottableType
import com.daniil.shevtsov.detective.feature.game.domain.gameState
import com.daniil.shevtsov.detective.feature.game.domain.slottable
import org.junit.jupiter.api.Test

internal class GamePresentationKtTest {
    @Test
    fun `should create initial state`() {
        val viewState = gamePresentation(
            state = gameState()
        )
        assertThat(viewState)
            .all {
                prop(GameViewState::time).isEmpty()
                prop(GameViewState::events).isEmpty()
                prop(GameViewState::place).isEmpty()
                prop(GameViewState::motive).all {
                    prop(MotiveModel::subject).isEmpty()
                    prop(MotiveModel::verb).isEmpty()
                    prop(MotiveModel::objectNoun).isEmpty()
                }
            }
    }

    @Test
    fun `should create set state`() {
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
                slottables = listOf(
                    slottable(
                        id = 1L,
                        value = "23-05-05",
                        type = SlottableType.Time
                    )
                )
            )
        )
        assertThat(viewState)
            .all {
                prop(GameViewState::time).isSet("23-04-29")
                prop(GameViewState::events).containsExactly(
                    "John Doe shot John Smith with .44 revolver",
                    "John Smith died of Gunshot Wound",
                    "John Doe took golden idol",
                )
                prop(GameViewState::place).isSet("Apartment no. 34 of 246 Green Street")
                prop(GameViewState::motive).all {
                    prop(MotiveModel::subject).isSet("John Smith")
                    prop(MotiveModel::verb).isSet("took")
                    prop(MotiveModel::objectNoun).isSet("golden idol")
                }
                prop(GameViewState::trayWords)
                    .extracting(SlottableModel::text)
                    .containsOnly("23-05-05")
            }
    }

    private fun Assert<SlotModel>.isEmpty() = isInstanceOf(SlotModel.Empty::class)
    private fun Assert<SlotModel>.isSet(expected: String) =
        isInstanceOf(SlotModel.Set::class)
            .prop(SlotModel.Set::value)
            .prop(SlottableModel::text)
            .isEqualTo(expected)
}
