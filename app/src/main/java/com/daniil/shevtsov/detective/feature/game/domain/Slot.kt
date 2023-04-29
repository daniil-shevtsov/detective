package com.daniil.shevtsov.detective.feature.game.domain

import org.jetbrains.annotations.TestOnly

data class Slot(
    val id: Long,
    val content: Slottable?,
    val type: SlottableType,
) : FormElement

@TestOnly
fun slot(
    id: Long = 0L,
    content: Slottable? = null,
    type: SlottableType = SlottableType.Noun,
) = Slot(
    id = id,
    content = content,
    type = type,
)
