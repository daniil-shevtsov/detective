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
    fun `init sanity check`() {
        val state = gameFunctionalCore(
            state = appState(gameState = gameState()),
            action = GameAction.Init
        )

        assertThat(state)
            .prop(AppState::gameState)
            .all {
                prop(GameState::slottables).any {
                    it.prop(Slottable::value).isEqualTo("John Doe")
                }
                prop(GameState::slots).any {
                    it.prop(FormLine::elements).any {
                        it.isInstanceOf(Slot::class).prop(Slot::content).isNull()
                    }
                }
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
                slotHasSlottable(expectedSlotId = slot.id, expectedSlottableId = slottable.id)
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
