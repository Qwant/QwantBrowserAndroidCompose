package com.qwant.android.qwantbrowser.mozac.hilt

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.qwant.android.qwantbrowser.legacy.bookmarks.BookmarksStorage
import com.qwant.android.qwantbrowser.legacy.history.History
import com.qwant.android.qwantbrowser.mozac.downloads.DownloadService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mozilla.components.browser.icons.BrowserIcons
import mozilla.components.browser.session.storage.SessionStorage
import mozilla.components.browser.state.engine.EngineMiddleware
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.browser.thumbnails.ThumbnailsMiddleware
import mozilla.components.browser.thumbnails.storage.ThumbnailStorage
import mozilla.components.concept.engine.Engine
import mozilla.components.concept.fetch.Client
import mozilla.components.concept.storage.HistoryStorage
import mozilla.components.feature.downloads.DownloadMiddleware
import mozilla.components.feature.downloads.DownloadStorage
import mozilla.components.feature.downloads.manager.DownloadManager
import mozilla.components.feature.downloads.manager.FetchDownloadManager
import mozilla.components.feature.prompts.PromptMiddleware
import mozilla.components.feature.pwa.ManifestStorage
import mozilla.components.feature.pwa.WebAppShortcutManager
import mozilla.components.support.base.android.NotificationsDelegate
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object MozacComponentHiltModule {
    @Singleton
    @Provides
    fun provideBrowserStore(
        @ApplicationContext context: Context,
        engine: Engine,
        downloadStorage: DownloadStorage,
        thumbnailStorage: ThumbnailStorage
    ) : BrowserStore {
        return BrowserStore(
            middleware = listOf(
                DownloadMiddleware(context, DownloadService::class.java, downloadStorage = downloadStorage),
                ThumbnailsMiddleware(thumbnailStorage),
                PromptMiddleware(),
            ) + EngineMiddleware.create(engine)
        )
    }

    @Singleton
    @Provides
    fun provideThumbnailStorage(
        @ApplicationContext context: Context
    ) : ThumbnailStorage {
        return ThumbnailStorage(context)
    }

    @Singleton
    @Provides
    fun provideDownloadStorage(
        @ApplicationContext context: Context
    ) : DownloadStorage {
        return DownloadStorage(context)
    }

    @Singleton
    @Provides
    fun provideHistoryStorage(
        @ApplicationContext context: Context
    ) : HistoryStorage {
        return History(context).apply {
            this.restore()
            this.setupAutoPersist(30000)
        }
    }

    @Singleton
    @Provides
    fun provideBookmarkStorage(
        @ApplicationContext context: Context
    ) : BookmarksStorage {
        return BookmarksStorage(context).apply {
            this.restore()
        }
    }

    @Singleton
    @Provides
    fun provideSessionStorage(
        @ApplicationContext context: Context,
        engine: Engine
    ) : SessionStorage {
        return SessionStorage(context, engine)
    }

    @Singleton
    @Provides
    fun provideBrowserIcons(
        @ApplicationContext context: Context,
        engine: Engine,
        client: Client,
        store: BrowserStore
    ) : BrowserIcons {
        return BrowserIcons(context, client).apply {
            this.install(engine, store)
        }
    }

    @Singleton
    @Provides
    fun provideShortcutManager(
        @ApplicationContext context: Context,
        client: Client,
    ) : WebAppShortcutManager {
        return WebAppShortcutManager(context, client, ManifestStorage(context))
    }

    @Singleton
    @Provides
    fun provideDownloadManager(
        @ApplicationContext context: Context,
        store: BrowserStore,
        notificationsDelegate: NotificationsDelegate
    ) : DownloadManager {
        return FetchDownloadManager(context, store, DownloadService::class,
            notificationsDelegate = notificationsDelegate
        )
    }

    @Singleton
    @Provides
    fun provideNotificationDelegate(
        @ApplicationContext context: Context
    ) : NotificationsDelegate {
        return NotificationsDelegate(NotificationManagerCompat.from(context))
    }
}
