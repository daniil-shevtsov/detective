package com.daniil.shevtsov.detective.core.navigation

import com.daniil.shevtsov.detective.feature.game.presentation.ContentViewState


sealed class ScreenViewState {
    object Loading : ScreenViewState()
    data class Main(
        val content: ContentViewState,
    ) : ScreenViewState()
}
