package com.daniil.shevtsov.detective.feature.game.domain

import assertk.Assert
import assertk.all
import assertk.assertThat
import assertk.assertions.*
import assertk.assertions.support.expected
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

    @Test
    fun `should drop slottable into an empty slot`() {
        val slottable = slottable(id = 0L, value = "lol")
        val slot = slot(id = 1L, content = null)
        val state = gameFunctionalCore(
            state = appState(
                gameState = gameState(
                    slottables = listOf(slottable),
                    slots = slots(listOf(slot))
                )
            ),
            action = GameAction.SlottableDrop(slotId = slot.id, slottableId = slottable.id)
        )

        assertThat(state)
            .prop(AppState::gameState)
            .all {
                slotHasSlottable(expectedSlotId = slot.id, expectedSlottableId = slottable.id)
                prop(GameState::slottables).containsExactly(slottable)
            }
    }

    @Test
    fun `should replace slottable in slot with another`() {
        val oldSlottable = slottable(id = 0L, value = "old lol")
        val slottable = slottable(id = 1L, value = "lol")
        val slot = slot(id = 2L, content = oldSlottable)
        val state = gameFunctionalCore(
            state = appState(
                gameState = gameState(
                    slottables = listOf(oldSlottable, slottable),
                    slots = slots(listOf(slot))
                )
            ),
            action = GameAction.SlottableDrop(slotId = slot.id, slottableId = slottable.id)
        )

        assertThat(state)
            .prop(AppState::gameState)
            .all {
                prop(GameState::slots)
                    .index(0)
                    .prop(FormLine::elements)
                    .index(0)
                    .isInstanceOf(Slot::class)
                    .prop(Slot::content)
                    .isEqualTo(slottable)
                prop(GameState::slottables).containsExactly(oldSlottable, slottable)
            }
    }

    private fun Assert<GameState>.slotHasSlottable(
        expectedSlotId: Long,
        expectedSlottableId: Long
    ) = given { gameState ->
        val slot = gameState.slots.mapNotNull { formLine ->
            formLine.elements.find { formElement ->
                formElement is Slot && formElement.id == expectedSlotId
            }
        }.firstOrNull() as? Slot
        val slottable =
            gameState.slottables.find { slottable -> slottable.id == expectedSlottableId }

        if (slot != null && slottable != null && slot.content == slottable) {
            return@given
        }

        val expectedMessage = "Expected slot$expectedSlotId to have slottable$expectedSlottableId"

        val problem = when {
            slot == null -> "slot$expectedSlotId does not exist"
            slottable == null -> "slottable$expectedSlottableId does not exist"
            slot.content == null -> "slot$expectedSlotId is empty"
            slot.content != slottable -> "slot$expectedSlotId has another slottable (slottable${slot.content?.id})"
            else -> "unknown problem"
        }

        expected(message = "$expectedMessage but $problem")
    }

    private fun slots(slots: List<FormElement>): List<FormLine> = listOf(FormLine(slots))
}
