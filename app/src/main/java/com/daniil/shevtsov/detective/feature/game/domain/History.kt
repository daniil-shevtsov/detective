package com.daniil.shevtsov.detective.feature.game.domain

data class History(
    val events: List<GameEvent>,
)

fun history(events: List<GameEvent> = emptyList()) = History(
    events = events,
)
