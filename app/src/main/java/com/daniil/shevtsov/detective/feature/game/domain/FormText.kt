package com.daniil.shevtsov.detective.feature.game.domain

import org.jetbrains.annotations.TestOnly

data class FormText(val value: String) : FormElement

@TestOnly
fun formText(value: String = "") = FormText(value)
