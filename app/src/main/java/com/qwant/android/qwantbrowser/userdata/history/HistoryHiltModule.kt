package com.qwant.android.qwantbrowser.userdata.history

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HistoryHiltModule {
    @Singleton
    @Provides fun provideHistoryDatabase(@ApplicationContext context: Context)
    : HistoryDatabase {
        return HistoryDatabase.create(context)
    }
}