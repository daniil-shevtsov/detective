package com.daniil.shevtsov.detective.feature.game.domain

data class Slottable(
    val id: SlottableId,
    val value: String,
    val type: SlottableType
)

fun slottable(
    id: Long = 0L,
    value: String = "",
    type: SlottableType = SlottableType.Noun,
) = Slottable(
    id = SlottableId(id),
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
