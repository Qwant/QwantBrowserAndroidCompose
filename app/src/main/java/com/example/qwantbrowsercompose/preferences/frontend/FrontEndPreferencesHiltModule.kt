package com.example.qwantbrowsercompose.preferences.frontend

import android.content.Context
import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object FrontEndPreferencesHiltModule {
    @Singleton
    @Provides fun provideFrontEndPreferencesDataStore(@ApplicationContext appContext: Context)
            : DataStore<FrontEndPreferences> {
        return FrontEndPreferencesFactory.create(appContext)
    }
}