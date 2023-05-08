package com.daniil.shevtsov.detective.feature.conversation.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.daniil.shevtsov.detective.feature.game.domain.GameAction
import com.daniil.shevtsov.detective.feature.game.presentation.ConversationViewState

@Composable
fun ConversationScreen(
    state: ConversationViewState,
    onAction: (GameAction) -> Unit
) {
    Text("CONVERSATION SCREEN")
}
