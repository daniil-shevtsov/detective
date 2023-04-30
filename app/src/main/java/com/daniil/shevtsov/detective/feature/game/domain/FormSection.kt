package com.daniil.shevtsov.detective.feature.game.domain

import org.jetbrains.annotations.TestOnly

data class FormSection(
    val title: String,
    val formLines: List<FormLine>,
)

@TestOnly
fun formSection(
    title: String = "",
    formLines: List<FormLine> = emptyList(),
) = FormSection(
    title = title,
    formLines = formLines,
)
