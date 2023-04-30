package com.daniil.shevtsov.detective.feature.game.presentation

import assertk.Assert
import assertk.all
import assertk.assertThat
import assertk.assertions.*
import com.daniil.shevtsov.detective.feature.game.domain.*
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
                prop(GameViewState::sections)
                    .extracting(FormSectionModel::title)
                    .containsExactly(
                        "When",
                        "Who and What",
                        "Where",
                        "Why",
                    )
            }
    }

    @Test
    fun `should create set state`() {
        val viewState = gamePresentation(
            state = gameState(
                slots = listOf(
                    formLine(listOf(slot(content = slottable(value = "23-04-29")))),
                    formLine(listOf(slot(content = slottable(value = "Apartment no. 34 of 246 Green Street")))),
                    formLine(
                        listOf(
                            slot(content = slottable(value = "John Smith")),
                            slot(content = slottable(value = "took")),
                            slot(content = slottable(value = "golden idol")),
                        )
                    ),
                    formLine(
                        listOf(
                            slot(content = slottable(value = "John Doe")),
                            slot(content = slottable(value = "shot")),
                            slot(content = slottable(value = "John Smith")),
                            formText(value = "with"),
                            slot(content = slottable(value = ".44 revolver")),
                        )
                    ),
                ),
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
                prop(GameViewState::events)
                    .extracting(FormLineModel::slots)
                    .all {
                        index(0).all {
                            index(0).isSet("John Doe")
                            index(1).isSet("shot")
                            index(2).isSet("John Smith")
                            index(3).isText("with")
                            index(4).isSet(".44 revolver")
                        }
                    }
                prop(GameViewState::place).isSet("Apartment no. 34 of 246 Green Street")
                prop(GameViewState::motive).all {
                    prop(MotiveModel::subject).isSet("John Smith")
                    prop(MotiveModel::verb).isSet("took")
                    prop(MotiveModel::objectNoun).isSet("golden idol")
                }
                prop(GameViewState::trayWords)
                    .extracting(SlottableModel::text)
                    .containsOnly("23-05-05")
                prop(GameViewState::sections)
                    .index(0)
                    .all {
                        prop(FormSectionModel::title).isEqualTo("When")
                        prop(FormSectionModel::lines)
                            .index(0)
                            .prop(FormLineModel::slots)
                            .index(0)
                            .isSet("23-04-29")
                    }
            }
    }

    private fun Assert<SlotModel>.isEmpty() = isInstanceOf(SlotModel.Empty::class)
    private fun Assert<SlotModel>.isSet(expected: String) = isInstanceOf(SlotModel.Set::class)
        .prop(SlotModel.Set::value)
        .prop(SlottableModel::text)
        .isEqualTo(expected)

    private fun Assert<SlotModel>.isText(expected: String) = isInstanceOf(SlotModel.Text::class)
        .prop(SlotModel.Text::text)
        .isEqualTo(expected)
}
