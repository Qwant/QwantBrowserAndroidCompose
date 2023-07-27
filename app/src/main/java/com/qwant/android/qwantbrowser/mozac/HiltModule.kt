package com.qwant.android.qwantbrowser.mozac

import android.content.Context
import com.qwant.android.qwantbrowser.userdata.history.HistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mozilla.components.feature.pwa.ManifestStorage
import mozilla.components.feature.pwa.WebAppShortcutManager
import javax.inject.Singleton

/* @Module
@InstallIn(SingletonComponent::class)
object MozacPwaHiltModule {
    @Singleton
    @Provides fun provideManifestStorage(
        @ApplicationContext context: Context)
    : ManifestStorage {
        return ManifestStorage(context)
    }

    @Singleton
    @Provides fun provideWebAppShortcutManager(
        @ApplicationContext context: Context)
    : WebAppShortcutManager {
        return WebAppShortcutManager(context)
    }

    @Singleton
    @Provides fun provideWebAppShortcutManager(
        @ApplicationContext context: Context)
            : WebAppShortcutManager {
        return WebAppShortcutManager()
    }
} */
