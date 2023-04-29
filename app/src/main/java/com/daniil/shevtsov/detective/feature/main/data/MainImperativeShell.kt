package com.daniil.shevtsov.detective.feature.main.data

import com.daniil.shevtsov.detective.core.di.AppScope
import com.daniil.shevtsov.detective.feature.coreshell.domain.AppState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@AppScope
class MainImperativeShell @Inject constructor(
    initialState: AppState
) {

    private val state = MutableStateFlow(initialState)

    fun getState(): AppState = state.value

    fun updateState(newState: AppState) {
        state.value = newState
    }

    fun observeState(): Flow<AppState> = state.asStateFlow()

}
