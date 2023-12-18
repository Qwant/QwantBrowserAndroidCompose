package com.qwant.android.qwantbrowser.mozac.hilt

import android.content.Context
import com.qwant.android.qwantbrowser.vip.QwantCookieFeature
import com.qwant.android.qwantbrowser.vip.QwantVIPFeature
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mozilla.components.browser.engine.gecko.GeckoEngine
import mozilla.components.browser.engine.gecko.fetch.GeckoViewFetchClient
import mozilla.components.browser.engine.gecko.permission.GeckoSitePermissionsStorage
import mozilla.components.concept.engine.Engine
import mozilla.components.concept.engine.Settings
import mozilla.components.concept.fetch.Client
import mozilla.components.feature.sitepermissions.OnDiskSitePermissionsStorage
import mozilla.components.feature.webcompat.WebCompatFeature
import mozilla.components.support.ktx.android.content.isMainProcess
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoRuntimeSettings
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object GeckoHiltModule {
    @Singleton
    @Provides
    @Synchronized
    fun provideGeckoRuntime(
        @ApplicationContext context: Context,
        settings: GeckoRuntimeSettings
    ): GeckoRuntime {
        assert(context.isMainProcess())
        return GeckoRuntime.create(context, settings)
    }

    @Singleton
    @Provides
    fun provideEngine(
        @ApplicationContext context: Context,
        runtime: GeckoRuntime,
        settings: Settings
    ): Engine {
        return GeckoEngine(context, settings, runtime).also {
            QwantVIPFeature.install(it)
            QwantCookieFeature.install(it)
            WebCompatFeature.install(it)
        }
    }

    @Singleton
    @Provides
    fun provideClient(
        @ApplicationContext context: Context,
        runtime: GeckoRuntime
    ): Client {
        return GeckoViewFetchClient(context, runtime)
    }

    @Singleton
    @Provides
    fun provideGeckoSitePermissionsStorage(
        @ApplicationContext context: Context,
        runtime: GeckoRuntime
    ): GeckoSitePermissionsStorage {
        return GeckoSitePermissionsStorage(runtime, OnDiskSitePermissionsStorage(context))
    }
}