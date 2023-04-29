package com.daniil.shevtsov.detective.application

import com.daniil.shevtsov.detective.feature.main.data.MainImperativeShell
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import javax.inject.Inject

class ApplicationViewModel @Inject constructor(
    private val imperativeShell: MainImperativeShell,
) {
    private val scope = CoroutineScope(Job() + Dispatchers.Main.immediate)

    fun onStart() {

    }

    fun onCleared() {
        scope.cancel()
    }

}
