package com.qwant.android.qwantbrowser.mozac.hilt

import android.content.Context
import com.qwant.android.qwantbrowser.AppRequestInterceptor
import com.qwant.android.qwantbrowser.ext.UA
import com.qwant.android.qwantbrowser.legacy.history.History
import dagger.Binds
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mozilla.components.concept.engine.DefaultSettings
import mozilla.components.concept.engine.Settings
import mozilla.components.concept.storage.HistoryStorage
import mozilla.components.feature.session.HistoryDelegate
import org.mozilla.geckoview.GeckoRuntimeSettings
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object GeckoSettingsHiltModule {
    @Singleton
    @Provides
    fun provideGeckoRuntimeSettings(
    ) : GeckoRuntimeSettings {
        val builder = GeckoRuntimeSettings.Builder()
        // TODO explore runtime settings
        // TODO runtime builder.contentBlocking(...)
        // TODO set debug attribute of runtime relative to build type
        builder.aboutConfigEnabled(true)
        builder.consoleOutput(true)
        builder.debugLogging(true)
        builder.remoteDebuggingEnabled(true)
        return builder.build()
    }


    @Singleton
    @Provides
    fun provideEngineSettings(
        @ApplicationContext context: Context,
        appRequestInterceptor: AppRequestInterceptor,
        historyStorage: Lazy<HistoryStorage>
    ) : Settings {
        return DefaultSettings(
            historyTrackingDelegate = HistoryDelegate(lazy { historyStorage.get() }),
            requestInterceptor = appRequestInterceptor,
            userAgentString = context.UA,
            // TODO move debugging options to gradle (or provide different source for variants ?)
            remoteDebuggingEnabled = true, // prefs.getBoolean(context.getPreferenceKey(pref_key_remote_debugging), false),
            testingModeEnabled = true
        )
    }
}