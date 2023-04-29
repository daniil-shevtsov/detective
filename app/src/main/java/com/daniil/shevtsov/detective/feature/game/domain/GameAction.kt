package com.daniil.shevtsov.detective.feature.game.domain

sealed interface GameAction {
    object Init : GameAction
}
