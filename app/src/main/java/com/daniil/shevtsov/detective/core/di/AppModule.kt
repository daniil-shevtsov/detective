package com.daniil.shevtsov.detective.core.di

import com.daniil.shevtsov.detective.core.di.viewmodel.ViewModelModule

import dagger.Module

@Module(
    includes = [
        ViewModelModule::class,
    ]
)
interface AppModule {

}
