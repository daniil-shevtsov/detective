package com.daniil.shevtsov.detective.feature.game.presentation

import com.daniil.shevtsov.detective.feature.game.ui.TextModel

data class ConversationViewState(
    val conversationLog: List<TextModel>,
) : ContentViewState

fun conversationViewStateCompose(
    conversationLog: List<TextModel> = listOf()
) = ConversationViewState(
    conversationLog = conversationLog
)
