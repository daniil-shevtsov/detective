package com.daniil.shevtsov.detective.feature.game.domain

data class Slottable(
    val id: Long,
    val value: String,
    val type: SlottableType
)

enum class SlottableType {
    Person,
    Verb,
    Noun,
    Place,
    Time,
}
