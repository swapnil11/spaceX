package com.example.finalplayground.di

import com.example.finalplayground.data.AppRepositoryImpl
import com.example.finalplayground.domain.respository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingModule {

    /**
     * Binds the [AppRepository] interface to its implementation class [AppRepositoryImpl]
     */
    @Binds
    abstract fun bindsProvideAppRepository(appRepositoryImpl: AppRepositoryImpl): AppRepository
}
