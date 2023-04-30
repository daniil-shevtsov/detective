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
                prop(GameViewState::sections).isEmpty()
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
                ),
                formSections = listOf(
                    formSection(
                        title = "When",
                        formLines = listOf(formLine(listOf(slot(content = slottable(value = "23-04-29")))))
                    ),
                    formSection(
                        title = "Where",
                        formLines = listOf(formLine(listOf(slot(content = slottable(value = "Apartment no. 34 of 246 Green Street")))))
                    ),
                    formSection(
                        title = "Who and What", formLines = listOf(
                            formLine(
                                listOf(
                                    slot(content = slottable(value = "John Doe")),
                                    slot(content = slottable(value = "shot")),
                                    slot(content = slottable(value = "John Smith")),
                                    formText(value = "with"),
                                    slot(content = slottable(value = ".44 revolver")),
                                )
                            )
                        )
                    ),
                    formSection(
                        title = "Why", formLines = listOf(
                            formLine(
                                listOf(
                                    slot(content = slottable(value = "John Smith")),
                                    slot(content = slottable(value = "took")),
                                    slot(content = slottable(value = "golden idol")),
                                )
                            )
                        )
                    ),
                )
            )
        )
        assertThat(viewState)
            .all {
                prop(GameViewState::trayWords)
                    .extracting(SlottableModel::text)
                    .containsOnly("23-05-05")
                prop(GameViewState::sections)
                    .all {
                        index(0)
                            .all {
                                prop(FormSectionModel::title).isEqualTo("When")
                                prop(FormSectionModel::lines)
                                    .index(0)
                                    .prop(FormLineModel::slots)
                                    .index(0)
                                    .isSet("23-04-29")
                            }
                        index(1)
                            .all {
                                prop(FormSectionModel::title).isEqualTo("Where")
                                prop(FormSectionModel::lines)
                                    .index(0)
                                    .prop(FormLineModel::slots)
                                    .index(0)
                                    .isSet("Apartment no. 34 of 246 Green Street")
                            }
                        index(2)
                            .all {
                                prop(FormSectionModel::title).isEqualTo("Who and What")
                                prop(FormSectionModel::lines)
                                    .index(0)
                                    .prop(FormLineModel::slots)
                                    .all {
                                        index(0).isSet("John Doe")
                                        index(1).isSet("shot")
                                        index(2).isSet("John Smith")
                                        index(3).isText("with")
                                        index(4).isSet(".44 revolver")
                                    }
                            }
                        index(3)
                            .all {
                                prop(FormSectionModel::title).isEqualTo("Why")
                                prop(FormSectionModel::lines)
                                    .index(0)
                                    .prop(FormLineModel::slots)
                                    .all {
                                        index(0).isSet("John Smith")
                                        index(1).isSet("took")
                                        index(2).isSet("golden idol")
                                    }
                            }
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
