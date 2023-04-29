package com.daniil.shevtsov.detective.feature.game.presentation

sealed interface SlotModel {
    val id: Long

    data class Empty(override val id: Long) : SlotModel
    data class Set(
        override val id: Long,
        val value: SlottableModel
    ) : SlotModel
}
