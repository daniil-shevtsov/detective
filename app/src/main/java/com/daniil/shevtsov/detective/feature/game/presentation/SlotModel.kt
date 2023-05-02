package com.daniil.shevtsov.detective.feature.game.presentation

import com.daniil.shevtsov.detective.feature.game.domain.SlotId

sealed interface SlotModel {
    val id: SlotId

    data class Empty(override val id: SlotId) : SlotModel
    data class Set(
        override val id: SlotId,
        val value: SlottableModel
    ) : SlotModel

    data class Text(override val id: SlotId, val text: String) : SlotModel
}
