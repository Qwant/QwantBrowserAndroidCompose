package com.example.qwantbrowsercompose.mozac

import mozilla.components.feature.session.SessionUseCases
import mozilla.components.feature.tabs.TabsUseCases


class UseCases(
    private val core: Core
) {
    val sessionUseCases by lazy { SessionUseCases(core.store) }
    val tabsUseCases: TabsUseCases by lazy { TabsUseCases(core.store) }
}
