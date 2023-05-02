package com.daniil.shevtsov.detective.feature.game.domain

sealed interface GameAction {
    data class SlottableDrop(val slotId: Long, val slottableId: SlottableId) : GameAction

    object Init : GameAction
}
