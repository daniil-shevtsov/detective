package com.daniil.shevtsov.detective.feature.game.domain

data class Actor(
    val id: Id,
    val name: Name,
) {
    @JvmInline
    value class Id(val raw: Long)

    @JvmInline
    value class Name(val raw: String)
}

fun actor(
    id: Long = 0L,
    name: String = "",
) = Actor(
    id = Actor.Id(id),
    name = Actor.Name(name),
)




