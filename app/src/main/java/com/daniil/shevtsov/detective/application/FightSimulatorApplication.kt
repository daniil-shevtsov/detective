package com.daniil.shevtsov.detective.application

import android.app.Application
import com.daniil.shevtsov.detective.common.di.initKoin
import com.daniil.shevtsov.detective.core.di.DaggerAppComponent
import com.daniil.shevtsov.detective.core.di.koin.appModule
import com.daniil.shevtsov.detective.feature.coreshell.domain.appState
import org.koin.core.Koin
import timber.log.Timber
import javax.inject.Inject

class DetectiveApplication : Application() {
    lateinit var koin: Koin
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

        koin = initKoin {
            modules(appModule)
        }.koin

        appComponent.inject(this)

        viewModel.onStart()
    }

    override fun onTerminate() {
        viewModel.onCleared()
        super.onTerminate()
    }

}
