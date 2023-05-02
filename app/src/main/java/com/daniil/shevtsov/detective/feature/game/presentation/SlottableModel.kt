package com.daniil.shevtsov.detective.feature.game.presentation

import com.daniil.shevtsov.detective.feature.game.domain.SlottableId

data class SlottableModel(
    val id: SlottableId,
    val text: String,
)
