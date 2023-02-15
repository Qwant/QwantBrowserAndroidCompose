package com.example.qwantbrowsercompose.mozac

import com.example.qwantbrowsercompose.preferences.frontend.FrontEndPreferencesRepository
import com.example.qwantbrowsercompose.usecases.QwantUseCases
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
