package com.daniil.shevtsov.detective.core.di

import android.content.Context
import com.daniil.shevtsov.detective.application.DetectiveApplication
import com.daniil.shevtsov.detective.core.navigation.ScreenHostFragment
import com.daniil.shevtsov.detective.feature.coreshell.domain.AppState
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        AppModule::class,
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appContext: Context,
            @BindsInstance initialAppState: AppState,
        ): AppComponent
    }

    fun inject(screenHostFragment: ScreenHostFragment)
    fun inject(application: DetectiveApplication)
}
