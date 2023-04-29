package com.daniil.shevtsov.detective.feature.game.domain

data class Slot(
    val id: Long,
    val content: Slottable?,
    val type: SlottableType,
)
