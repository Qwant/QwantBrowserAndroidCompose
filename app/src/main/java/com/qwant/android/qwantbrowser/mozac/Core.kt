package com.qwant.android.qwantbrowser.mozac

import android.content.Context
import mozilla.components.browser.session.storage.SessionStorage
import mozilla.components.browser.state.engine.EngineMiddleware
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.browser.thumbnails.ThumbnailsMiddleware
import mozilla.components.browser.thumbnails.storage.ThumbnailStorage
import mozilla.components.concept.engine.DefaultSettings
import mozilla.components.concept.engine.Engine
import mozilla.components.concept.fetch.Client
import mozilla.components.feature.session.HistoryDelegate
import com.qwant.android.qwantbrowser.legacy.history.History
import com.qwant.android.qwantbrowser.mozac.downloads.DownloadService
import mozilla.components.browser.engine.gecko.permission.GeckoSitePermissionsStorage
import mozilla.components.feature.downloads.DownloadMiddleware
import mozilla.components.feature.downloads.DownloadStorage
import mozilla.components.feature.downloads.manager.FetchDownloadManager
import mozilla.components.feature.sitepermissions.OnDiskSitePermissionsStorage


/**
 * Component group for all core browser functionality.
 */
class Core(private val context: Context) {
    val engine: Engine by lazy {
        val defaultSettings = DefaultSettings(
            historyTrackingDelegate = HistoryDelegate(lazy { historyStorage })
            /* requestInterceptor = AppRequestInterceptor(context),
            remoteDebuggingEnabled = false, // prefs.getBoolean(context.getPreferenceKey(pref_key_remote_debugging), false),
            trackingProtectionPolicy = TrackingProtectionPolicy.recommended(), // createTrackingProtectionPolicy(prefs),
            testingModeEnabled = false,
            userAgentString = context.getString(R.string.qwant_base_useragent) + context.getString(R.string.qwant_useragent_ext) */
        )
        EngineProvider.createEngine(context, defaultSettings)
    }

    val client: Client by lazy {
        EngineProvider.createClient(context)
    }

    val store by lazy {
        BrowserStore(
            middleware = listOf(
                DownloadMiddleware(context, DownloadService::class.java, downloadStorage = downloadStorage),
                ThumbnailsMiddleware(thumbnailStorage),
            ) + EngineMiddleware.create(engine)
        )
    }

    val sessionStorage: SessionStorage by lazy {
        SessionStorage(context, engine)
    }

    val thumbnailStorage by lazy { ThumbnailStorage(context) }

    val historyStorage by lazy { History(context).apply {
        this.restore()
        this.setupAutoPersist(30000)
    } }

    val downloadStorage by lazy { DownloadStorage(context) }
    val downloadManager by lazy { FetchDownloadManager(context, store, DownloadService::class) }

    val geckoSitePermissionsStorage by lazy {
        val geckoRuntime = EngineProvider.getOrCreateRuntime(context)
        GeckoSitePermissionsStorage(geckoRuntime, OnDiskSitePermissionsStorage(context))
    }
}
