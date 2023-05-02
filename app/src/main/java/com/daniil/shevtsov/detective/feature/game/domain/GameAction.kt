package com.daniil.shevtsov.detective.feature.game.domain

sealed interface GameAction {
    data class SlottableDrop(val slotId: SlotId, val slottableId: SlottableId) : GameAction

    object Init : GameAction
}
