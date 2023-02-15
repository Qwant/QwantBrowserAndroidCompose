package com.qwant.android.qwantbrowser.mozac

import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesRepository
import com.qwant.android.qwantbrowser.usecases.QwantUseCases
import mozilla.components.feature.session.SessionUseCases
import mozilla.components.feature.tabs.TabsUseCases


class UseCases (
    private val core: Core,
    private val frontEndPreferencesRepository: FrontEndPreferencesRepository
) {
    val sessionUseCases by lazy { SessionUseCases(core.store) }
    val tabsUseCases: TabsUseCases by lazy { TabsUseCases(core.store) }

    val qwantUseCases: QwantUseCases by lazy {
        QwantUseCases(frontEndPreferencesRepository, sessionUseCases)
    }
}
