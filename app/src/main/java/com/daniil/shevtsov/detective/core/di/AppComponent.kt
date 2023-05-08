package com.daniil.shevtsov.detective.core.di

import android.content.Context
import com.daniil.shevtsov.detective.application.DetectiveApplication
import com.daniil.shevtsov.detective.core.navigation.ScreenHostFragment
import com.daniil.shevtsov.detective.feature.game.domain.GameState
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
            @BindsInstance initialGameState: GameState,
        ): AppComponent
    }

    fun inject(screenHostFragment: ScreenHostFragment)
    fun inject(application: DetectiveApplication)
}
