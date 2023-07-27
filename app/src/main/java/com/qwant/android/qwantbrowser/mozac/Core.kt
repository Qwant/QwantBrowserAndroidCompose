package com.qwant.android.qwantbrowser.mozac

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.qwant.android.qwantbrowser.AppRequestInterceptor
import com.qwant.android.qwantbrowser.legacy.bookmarks.BookmarksStorage
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
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mozilla.components.browser.engine.gecko.permission.GeckoSitePermissionsStorage
import mozilla.components.browser.icons.BrowserIcons
import mozilla.components.concept.base.crash.CrashReporting
import mozilla.components.concept.engine.EngineSession
import mozilla.components.feature.app.links.AppLinksInterceptor
import mozilla.components.feature.downloads.DownloadMiddleware
import mozilla.components.feature.downloads.DownloadStorage
import mozilla.components.feature.downloads.manager.FetchDownloadManager
import mozilla.components.feature.prompts.PromptMiddleware
import mozilla.components.feature.pwa.ManifestStorage
import mozilla.components.feature.pwa.WebAppShortcutManager
import mozilla.components.feature.sitepermissions.OnDiskSitePermissionsStorage
import mozilla.components.support.base.android.NotificationsDelegate
import javax.inject.Inject
import javax.inject.Singleton


// TODO More hilt injection for mozac core components
/**
 * Component group for all core browser functionality.
 */
@Singleton
class Core @Inject constructor(@ApplicationContext private val context: Context) {
    @Inject lateinit var appRequestInterceptor: AppRequestInterceptor

    val engine: Engine by lazy {
        val defaultSettings = DefaultSettings(
            historyTrackingDelegate = HistoryDelegate(lazy { historyStorage }),
            requestInterceptor = appRequestInterceptor,
            // TODO centralise ua
            userAgentString = "Mozilla/5.0 (Android 10; Mobile; rv:115.0) Gecko/115.0 Firefox/115.0" // context.getString(R.string.qwant_base_useragent) + context.getString(R.string.qwant_useragent_ext)
             /* remoteDebuggingEnabled = false, // prefs.getBoolean(context.getPreferenceKey(pref_key_remote_debugging), false),
             trackingProtectionPolicy = TrackingProtectionPolicy.recommended(), // createTrackingProtectionPolicy(prefs),
             testingModeEnabled = false, */
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
                PromptMiddleware(),
            ) + EngineMiddleware.create(engine)
        )
    }

    val sessionStorage: SessionStorage by lazy {
        SessionStorage(context, engine)
    }

    val thumbnailStorage by lazy { ThumbnailStorage(context) }

    val browserIcons by lazy { BrowserIcons(context, client).apply {
        this.install(engine, store)
    } }

    val shortcutManager by lazy { WebAppShortcutManager(context, client, ManifestStorage(context)) }

    val historyStorage by lazy { History(context).apply {
        this.restore()
        this.setupAutoPersist(30000)
    } }

    val bookmarkStorage by lazy { BookmarksStorage(context).apply {
        this.restore()
    } }

    private val notificationManagerCompat = NotificationManagerCompat.from(context)
    val notificationsDelegate: NotificationsDelegate by lazy { NotificationsDelegate(notificationManagerCompat) }

    val downloadStorage by lazy { DownloadStorage(context) }
    val downloadManager by lazy {
        FetchDownloadManager(context, store, DownloadService::class, notificationsDelegate = notificationsDelegate)
    }

    val geckoSitePermissionsStorage by lazy {
        val geckoRuntime = EngineProvider.getOrCreateRuntime(context)
        GeckoSitePermissionsStorage(geckoRuntime, OnDiskSitePermissionsStorage(context))
    }
}
