package com.qwant.android.qwantbrowser.mozac

import android.content.Context
import mozilla.components.browser.session.storage.SessionStorage
import mozilla.components.browser.state.engine.EngineMiddleware
import mozilla.components.browser.state.state.BrowserState
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.browser.thumbnails.ThumbnailsMiddleware
import mozilla.components.browser.thumbnails.storage.ThumbnailStorage
import mozilla.components.concept.engine.DefaultSettings
import mozilla.components.concept.engine.Engine
import mozilla.components.concept.fetch.Client


/**
 * Component group for all core browser functionality.
 */
class Core(private val context: Context) {
    val engine: Engine by lazy {
        val defaultSettings = DefaultSettings(
            /* requestInterceptor = AppRequestInterceptor(context),
            remoteDebuggingEnabled = false, // prefs.getBoolean(context.getPreferenceKey(pref_key_remote_debugging), false),
            trackingProtectionPolicy = TrackingProtectionPolicy.recommended(), // createTrackingProtectionPolicy(prefs),
            historyTrackingDelegate = HistoryDelegate(lazy { historyStorage }),
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
                ThumbnailsMiddleware(thumbnailStorage),
            ) + EngineMiddleware.create(engine)
        )
    }

    val sessionStorage: SessionStorage by lazy {
        SessionStorage(context, engine)
    }

    val thumbnailStorage by lazy { ThumbnailStorage(context) }
}
