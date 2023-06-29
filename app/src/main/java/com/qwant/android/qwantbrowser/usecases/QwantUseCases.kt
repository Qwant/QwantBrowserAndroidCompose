package com.qwant.android.qwantbrowser.usecases

import android.content.Context
import android.util.Log
import com.qwant.android.qwantbrowser.mozac.Core
import com.qwant.android.qwantbrowser.preferences.app.AppPreferences
import com.qwant.android.qwantbrowser.preferences.app.AppPreferencesRepository
import com.qwant.android.qwantbrowser.preferences.app.ClearDataPreferences
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import mozilla.components.concept.engine.Engine
import mozilla.components.concept.storage.HistoryStorage
import mozilla.components.feature.session.SessionUseCases
import mozilla.components.feature.tabs.TabsUseCases
import mozilla.components.support.ktx.kotlin.urlEncode

// TODO separate QwantUseCases into multiple use cases
class QwantUseCases(
    context: Context,
    core: Core,
    appPreferencesRepository: AppPreferencesRepository,
    frontEndPreferencesRepository: FrontEndPreferencesRepository,
    sessionUseCases: SessionUseCases,
    tabsUseCases: TabsUseCases,
) {
    private val privateBrowsingHtml = context.assets.open("privatebrowsing.html")
        .bufferedReader().use {
            it.readText()
        }

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
                invoke(private)
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

        operator fun invoke(coroutineScope: CoroutineScope, search: String) {
            coroutineScope.launch {
                invoke(search)
            }
        }
    }

    class OpenPrivatePageUseCase internal constructor(
        private val tabsUseCases: TabsUseCases,
        private val sessionUseCases: SessionUseCases,
        private val HtmlContent: String
    ) {
        operator fun invoke() {
            sessionUseCases.loadData(
                data = HtmlContent,
                mimeType = "text/html",
                tabId = tabsUseCases.addTab(
                    "about:privatebrowsing",
                    selectTab = true,
                    private = true
                )
            )
        }
    }

    class ClearDataUseCase internal constructor(
        val prefs: AppPreferencesRepository,
        val engine: Engine,
        val historyStorage: HistoryStorage,
        val tabsUseCases: TabsUseCases
    ) {
        operator fun invoke(clearDataPreferences: ClearDataPreferences, then: (Boolean) -> Unit = {}) {
            val clearHistoryJob: Job? = if (clearDataPreferences.history) {
                MainScope().launch {
                    historyStorage.deleteEverything()
                }
            } else null

            if (clearDataPreferences.tabs) {
                tabsUseCases.removeAllTabs()
            } else {
                // Always remove private tabs, no matter clearDataPreferences
                tabsUseCases.removePrivateTabs()
            }

            val onBrowsingDataComplete: (Boolean) -> Unit = { browsingDataSuccess ->
                if (clearHistoryJob?.isActive == true) {
                    clearHistoryJob.invokeOnCompletion { clearHistoryException ->
                        if (clearHistoryException != null) {
                            Log.e("QWANT_BROWSER", "Failed to clear history storage: ${clearHistoryException.message}")
                            then(false)
                        } else {
                            then(browsingDataSuccess)
                        }
                    }
                } else {
                    then(browsingDataSuccess)
                }
            }

            if (clearDataPreferences.browsingData.types != 0) {
                engine.clearData(
                    clearDataPreferences.browsingData,
                    onSuccess = { onBrowsingDataComplete(true) },
                    onError = { onBrowsingDataComplete(false) }
                )
            } else {
                onBrowsingDataComplete(true)
            }
        }

        operator fun invoke(coroutineScope: CoroutineScope = MainScope(), then: (Boolean) -> Unit = {}) {
            coroutineScope.launch {
                val clearDataPrefs = prefs.clearDataPreferencesFlow.first()
                invoke(clearDataPrefs, then)
            }
        }
    }


    val openHomePage: OpenHomePageUseCase by lazy {
        OpenHomePageUseCase(frontEndPreferencesRepository, tabsUseCases)
    }
    val loadSERPPage: LoadSERPPageUseCase by lazy {
        LoadSERPPageUseCase(frontEndPreferencesRepository, sessionUseCases)
    }
    val openPrivatePage: OpenPrivatePageUseCase by lazy {
        OpenPrivatePageUseCase(tabsUseCases, sessionUseCases, privateBrowsingHtml)
    }
    val clearDataUseCase: ClearDataUseCase by lazy {
        ClearDataUseCase(appPreferencesRepository, core.engine, core.historyStorage, tabsUseCases)
    }
}