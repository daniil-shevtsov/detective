package com.daniil.shevtsov.detective.feature.game.domain

data class Actors(
    val list: List<Actor>
)

fun actors(list: List<Actor> = emptyList()) = Actors(list = list)
fun actors(vararg actors: Actor) = Actors(list = actors.toList())
