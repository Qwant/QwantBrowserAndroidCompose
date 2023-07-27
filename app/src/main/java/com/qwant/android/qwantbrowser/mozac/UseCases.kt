package com.qwant.android.qwantbrowser.mozac

import android.content.Context
import com.qwant.android.qwantbrowser.preferences.app.AppPreferencesRepository
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesRepository
import com.qwant.android.qwantbrowser.usecases.QwantUseCases
import dagger.hilt.android.qualifiers.ApplicationContext
import mozilla.components.feature.contextmenu.ContextMenuUseCases
import mozilla.components.feature.downloads.DownloadsUseCases
import mozilla.components.feature.pwa.WebAppUseCases
import mozilla.components.feature.session.SessionUseCases
import mozilla.components.feature.tabs.TabsUseCases
import javax.inject.Inject
import javax.inject.Singleton

// TODO Create hilt modules for usecases
@Singleton
class UseCases @Inject constructor(
    @ApplicationContext private val context: Context,
    private val core: Core,
    private val appPreferencesRepository: AppPreferencesRepository,
    private val frontEndPreferencesRepository: FrontEndPreferencesRepository
) {
    val sessionUseCases by lazy { SessionUseCases(core.store) }
    val tabsUseCases by lazy { TabsUseCases(core.store) }
    val contextMenuUseCases by lazy { ContextMenuUseCases(core.store) }
    val downloadUseCases by lazy { DownloadsUseCases(core.store) }
    val webAppUseCases by lazy { WebAppUseCases(context, core.store, core.shortcutManager) }

    val qwantUseCases by lazy {
        QwantUseCases(context, core, appPreferencesRepository, frontEndPreferencesRepository, sessionUseCases, tabsUseCases)
    }
}
