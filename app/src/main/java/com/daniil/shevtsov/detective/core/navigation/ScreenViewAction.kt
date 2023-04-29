package com.daniil.shevtsov.detective.core.navigation

import com.daniil.shevtsov.detective.feature.game.domain.GameAction

sealed class ScreenViewAction {
    data class General(val action: GeneralViewAction) : ScreenViewAction()
    data class Game(val action: GameAction) : ScreenViewAction()
}
