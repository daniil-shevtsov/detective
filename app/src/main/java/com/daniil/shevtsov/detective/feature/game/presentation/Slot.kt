package com.daniil.shevtsov.detective.feature.game.presentation

sealed interface Slot {
    object Empty : Slot
    data class Set(val value: String) : Slot
}
