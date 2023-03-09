package com.qwant.android.qwantbrowser.usecases

import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import mozilla.components.feature.session.SessionUseCases
import mozilla.components.feature.tabs.TabsUseCases
import mozilla.components.support.ktx.kotlin.urlEncode


class QwantUseCases(
    frontEndPreferencesRepository: FrontEndPreferencesRepository,
    sessionUseCases: SessionUseCases,
    tabsUseCases: TabsUseCases
) {
    class OpenHomePageUseCase internal constructor(
        private val frontEndPreferencesRepository: FrontEndPreferencesRepository,
        private val tabsUseCases: TabsUseCases
    ) {
        suspend operator fun invoke(private: Boolean = false) {
            tabsUseCases.addTab.invoke(
                frontEndPreferencesRepository.homeUrl.first(),
                selectTab = true,
                private = private
            )
        }

        operator fun invoke(coroutineScope: CoroutineScope, private: Boolean = false) {
            coroutineScope.launch {
                tabsUseCases.addTab.invoke(
                    frontEndPreferencesRepository.homeUrl.first(),
                    selectTab = true,
                    private = private
                )
            }
        }
    }

    class LoadSERPPageUseCase internal constructor(
        private val frontEndPreferencesRepository: FrontEndPreferencesRepository,
        private val sessionUseCases: SessionUseCases
    ) {
        suspend operator fun invoke(search: String) {
            val base = frontEndPreferencesRepository.homeUrl.first()
            sessionUseCases.loadUrl(url = "$base&q=${search.urlEncode()}")
        }

        operator fun invoke(search: String, coroutineScope: CoroutineScope) {
            coroutineScope.launch {
                val base = frontEndPreferencesRepository.homeUrl.first()
                sessionUseCases.loadUrl(url = "$base&q=${search.urlEncode()}")
            }
        }
    }

    val openHomePage: OpenHomePageUseCase by lazy {
        OpenHomePageUseCase(frontEndPreferencesRepository, tabsUseCases)
    }
    val loadSERPPage: LoadSERPPageUseCase by lazy {
        LoadSERPPageUseCase(frontEndPreferencesRepository, sessionUseCases)
    }
}