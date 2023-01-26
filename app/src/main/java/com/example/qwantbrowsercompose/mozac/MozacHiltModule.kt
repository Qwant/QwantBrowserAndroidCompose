package com.example.qwantbrowsercompose.mozac

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MozacHiltModule {
    @Singleton
    @Provides fun provideCore(@ApplicationContext context: Context)
    : Core {
        return Core(context = context)
    }

    @Singleton
    @Provides fun provideUseCases(core: Core)
    : UseCases {
        return UseCases(core)
    }
}