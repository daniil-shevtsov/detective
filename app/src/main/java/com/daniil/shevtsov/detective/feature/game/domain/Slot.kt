package com.daniil.shevtsov.detective.feature.game.domain

data class Slot(
    val id: SlotId,
    val content: Slottable?,
    val type: SlottableType,
) : FormElement

fun slot(
    id: Long = 0L,
    content: Slottable? = null,
    type: SlottableType = SlottableType.Noun,
) = Slot(
    id = SlotId(id),
    content = content,
    type = type,
)
