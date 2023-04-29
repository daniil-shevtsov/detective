package com.daniil.shevtsov.detective.feature.game.presentation

data class MotiveModel(
    val subject: SlotModel,
    val verb: SlotModel,
    val objectNoun: SlotModel,
)
