package com.qwant.android.qwantbrowser.usecases

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.qwant.android.qwantbrowser.mozac.Core
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

// TODO Hilt for QwantUseCases
// TODO separate QwantUseCases into multiple use cases ?
class QwantUseCases(
    val context: Context,
    core: Core,
    appPreferencesRepository: AppPreferencesRepository,
    frontEndPreferencesRepository: FrontEndPreferencesRepository,
    sessionUseCases: SessionUseCases,
    tabsUseCases: TabsUseCases,
) {
    private val coroutineScope = MainScope()

    var baseUrl = ""
    init {
        coroutineScope.launch {
            frontEndPreferencesRepository.homeUrl
                .collect { baseUrl = it }
        }
    }

    // Needed to get a lazy instance running before usage
    fun warmUp() {}

    inner class OpenQwantPageUseCase internal constructor(
        private val tabsUseCases: TabsUseCases,
    ) {
        private val firstRequestKey = "pref_key_first_request"

        @SuppressLint("ApplySharedPref")
        operator fun invoke(search: String? = null, private: Boolean = false, selectIfExists: Boolean = false) {
            var url = search?.let { "$baseUrl&q=${it.urlEncode()}" } ?: baseUrl

            // TODO find a different way of handling fs=1 one time parameter
            val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val firstRequest = prefs.getBoolean(firstRequestKey, true)
            if (firstRequest) {
                prefs.edit().putBoolean(firstRequestKey, false).commit()
                url += "&fs=1"
            }

            if (selectIfExists) {
                tabsUseCases.selectOrAddTab.invoke(url, private = private)
            } else {
                tabsUseCases.addTab.invoke(
                    url,
                    selectTab = true,
                    private = private
                )
            }
        }
    }

    inner class GetQwantUrlUseCase internal constructor() {
        operator fun invoke(search: String? = null, widget: Boolean = false): String {
            val withSearch = search?.let { "$baseUrl&q=${it.urlEncode()}" } ?: baseUrl
            return if (widget) "$withSearch + &widget=1" else withSearch
        }
    }

    inner class LoadSERPPageUseCase internal constructor(
        private val sessionUseCases: SessionUseCases
    ) {
        operator fun invoke(search: String) {
            sessionUseCases.loadUrl(url = "$baseUrl&q=${search.urlEncode()}")
        }
    }

    class OpenPrivatePageUseCase internal constructor(
        private val tabsUseCases: TabsUseCases
    ) {
        operator fun invoke() {
            tabsUseCases.addTab(
                "",
                selectTab = true,
                private = true
            )
        }
    }

    class OpenTestPageUseCase internal constructor(
        private val context: Context,
        private val tabsUseCases: TabsUseCases,
        private val sessionUseCases: SessionUseCases
    ) {
        operator fun invoke(test: String) {
            val testHtml = context.assets.open("tests/$test.html")
                .bufferedReader().use {
                    it.readText()
                }

            sessionUseCases.loadData(
                data = testHtml,
                mimeType = "text/html",
                tabId = tabsUseCases.addTab(
                    "about:test:$test",
                    selectTab = true
                )
            )
        }
    }

    class ClearDataUseCase internal constructor(
        private val prefs: AppPreferencesRepository,
        private val engine: Engine,
        private val historyStorage: HistoryStorage,
        private val tabsUseCases: TabsUseCases
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

    val openQwantPage: OpenQwantPageUseCase by lazy {
        OpenQwantPageUseCase(tabsUseCases)
    }
    val getQwantUrl: GetQwantUrlUseCase by lazy {
        GetQwantUrlUseCase()
    }
    val loadSERPPage: LoadSERPPageUseCase by lazy {
        LoadSERPPageUseCase(sessionUseCases)
    }
    val openPrivatePage: OpenPrivatePageUseCase by lazy {
        OpenPrivatePageUseCase(tabsUseCases)
    }
    val openTestPageUseCase: OpenTestPageUseCase by lazy {
        OpenTestPageUseCase(context, tabsUseCases, sessionUseCases)
    }
    val clearDataUseCase: ClearDataUseCase by lazy {
        ClearDataUseCase(appPreferencesRepository, core.engine, core.historyStorage, tabsUseCases)
    }
}