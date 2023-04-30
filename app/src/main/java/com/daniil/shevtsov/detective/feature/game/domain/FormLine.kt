package com.daniil.shevtsov.detective.feature.game.domain

import org.jetbrains.annotations.TestOnly

data class FormLine(
    val elements: List<FormElement>
)

@TestOnly
fun formLine(elements: List<FormElement> = emptyList()) = FormLine(elements)
