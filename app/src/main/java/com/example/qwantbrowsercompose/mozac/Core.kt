package com.example.qwantbrowsercompose.mozac

import android.content.Context
import mozilla.components.browser.session.storage.SessionStorage
import mozilla.components.browser.state.engine.EngineMiddleware
import mozilla.components.browser.state.state.BrowserState
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.concept.engine.DefaultSettings
import mozilla.components.concept.engine.Engine
import mozilla.components.concept.fetch.Client


/**
 * Component group for all core browser functionality.
 */
class Core(private val context: Context) {
    /**
     * The browser engine component initialized based on the build
     * configuration (see build variants).
     */
    val engine: Engine by lazy {
        // val prefs = PreferenceManager.getDefaultSharedPreferences(context)
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

    /**
     * The [Client] implementation (`concept-fetch`) used for HTTP requests.
     */
    val client: Client by lazy {
        EngineProvider.createClient(context)
    }

    /**
     * The [BrowserStore] holds the global [BrowserState].
     */
    val store by lazy {
        BrowserStore(
                middleware = /* listOf(
                        DownloadMiddleware(context, DownloadService::class.java),
                        ThumbnailsMiddleware(thumbnailStorage),
                        ReaderViewMiddleware(),
                        RegionMiddleware(
                                context,
                                LocationService.default()
                        ),
                        SearchMiddleware(context, listOf("qwant")),
                        RecordingDevicesMiddleware(context)
                ) + */ EngineMiddleware.create(engine)
        )
    }

    val sessionStorage: SessionStorage by lazy {
        SessionStorage(context, engine)
    }
}
