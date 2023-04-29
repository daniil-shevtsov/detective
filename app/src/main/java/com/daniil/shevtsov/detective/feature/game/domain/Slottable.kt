package com.daniil.shevtsov.detective.feature.game.domain

import org.jetbrains.annotations.TestOnly

data class Slottable(
    val id: Long,
    val value: String,
    val type: SlottableType
)

@TestOnly
fun slottable(
    id: Long = 0L,
    value: String = "",
    type: SlottableType = SlottableType.Noun,
) = Slottable(
    id = id,
    value = value,
    type = type,
)

enum class SlottableType {
    Person,
    Verb,
    Noun,
    Place,
    Time,
}
