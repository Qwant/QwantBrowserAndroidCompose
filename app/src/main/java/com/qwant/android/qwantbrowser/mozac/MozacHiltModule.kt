package com.qwant.android.qwantbrowser.mozac

import android.content.Context
import com.qwant.android.qwantbrowser.preferences.app.AppPreferencesRepository
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesRepository
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
    @Provides fun provideUseCases(
        @ApplicationContext context: Context,
        core: Core,
        appPreferencesRepository: AppPreferencesRepository,
        frontEndPreferencesRepository: FrontEndPreferencesRepository
    )
    : UseCases {
        return UseCases(context, core, appPreferencesRepository, frontEndPreferencesRepository)
    }
}