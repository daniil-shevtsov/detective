package com.daniil.shevtsov.detective.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daniil.shevtsov.detective.core.di.AppScope
import com.daniil.shevtsov.detective.core.navigation.ScreenHostViewModel
import com.daniil.shevtsov.detective.feature.main.data.MainImperativeShell

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ScreenHostViewModel::class)
    fun screenHostViewModel(viewModel: ScreenHostViewModel): ViewModel


    @Binds
    @AppScope
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
