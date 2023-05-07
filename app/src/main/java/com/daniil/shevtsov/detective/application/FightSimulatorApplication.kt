package com.daniil.shevtsov.detective.application

import android.app.Application
import com.daniil.shevtsov.detective.core.di.DaggerAppComponent
import com.daniil.shevtsov.detective.feature.main.domain.appState
import timber.log.Timber
import javax.inject.Inject

class DetectiveApplication : Application() {
    val appComponent by lazy {
        DaggerAppComponent
            .factory()
            .create(
                appContext = applicationContext,
                initialAppState = appState()
            )
    }

    @Inject
    lateinit var viewModel: ApplicationViewModel


    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        appComponent.inject(this)

        viewModel.onStart()
    }

    override fun onTerminate() {
        viewModel.onCleared()
        super.onTerminate()
    }

}
