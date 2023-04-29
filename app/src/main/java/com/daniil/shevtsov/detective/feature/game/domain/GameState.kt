package com.daniil.shevtsov.detective.feature.game.domain

data class GameState(
    val perpetrator: String,
    val victim: String,
    val time: String,
    val place: String,
    val deathCause: String,
    val weapon: String,
    val murderAction: String,
    val crimeAction: String,
    val stolenObject: String,
    val motive: String,
)

fun gameState(
    perpetrator: String = "",
    victim: String = "",
    time: String = "",
    place: String = "",
    deathCause: String = "",
    weapon: String = "",
    murderAction: String = "",
    crimeAction: String = "",
    stolenObject: String = "",
    motive: String = "",
) = GameState(
    perpetrator = perpetrator,
    victim = victim,
    time = time,
    place = place,
    deathCause = deathCause,
    weapon = weapon,
    murderAction = murderAction,
    crimeAction = crimeAction,
    stolenObject = stolenObject,
    motive = motive,
)
