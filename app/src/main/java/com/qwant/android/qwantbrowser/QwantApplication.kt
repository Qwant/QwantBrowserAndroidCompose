package com.qwant.android.qwantbrowser

import android.app.Application
import com.qwant.android.qwantbrowser.piwik.Piwik
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import mozilla.components.browser.session.storage.SessionStorage
import mozilla.components.browser.state.action.SystemAction
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.concept.engine.Engine
import mozilla.components.concept.fetch.Client
import mozilla.components.feature.tabs.TabsUseCases
import mozilla.components.support.base.log.Log
import mozilla.components.support.base.log.sink.AndroidLogSink
import mozilla.components.support.ktx.android.content.isMainProcess
import mozilla.components.support.ktx.android.content.runOnlyInMainProcess
import mozilla.components.support.rusthttp.RustHttpConfig
import mozilla.components.support.webextensions.WebExtensionSupport
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class QwantApplication : Application() {
    @Inject lateinit var engine: dagger.Lazy<Engine>
    @Inject lateinit var client: dagger.Lazy<Client>
    @Inject lateinit var store: dagger.Lazy<BrowserStore>
    @Inject lateinit var sessionStorage: dagger.Lazy<SessionStorage>
    @Inject lateinit var tabsUseCases: dagger.Lazy<TabsUseCases>
    @Inject lateinit var piwik: Piwik
    // @Inject lateinit var cookieFeature: dagger.Lazy<QwantCookieFeature>

    override fun onCreate() {
        super.onCreate()

        setupLogging()
        RustHttpConfig.setClient(lazy { client.get() })

        if (!isMainProcess()) {
            // If this is not the main process then do not continue with the initialization here. Everything that
            // follows only needs to be done in our app's main process and should not be done in other processes like
            // a GeckoView child process or the crash handling process. Most importantly we never want to end up in a
            // situation where we create a GeckoRuntime from the Gecko child process (
            return
        }

        engine.get().apply {
            warmUp()
            speculativeConnect("https://www.qwant.com/")
        }

        // cookieFeature.get().run()

        restoreBrowserState()

        piwik.trackApplicationDownload()
        piwik.event("App", "Opening")

        // TODO
        //  Should be removed in futur version, once mozilla has fully migrated
        //  meanwhile move this elsewhere
        val tuc = tabsUseCases.get()
        WebExtensionSupport.initialize(
            runtime = engine.get(),
            store = store.get(),
            openPopupInTab = false,
            onNewTabOverride = { _, engineSession, url ->
                val tabId = tuc.addTab(
                    url = url,
                    selectTab = true,
                    engineSession = engineSession
                )
                tabId
            },
            onCloseTabOverride = { _, sessionId ->
                tuc.removeTab(sessionId)
            },
            onSelectTabOverride = { _, sessionId ->
                tuc.selectTab(sessionId)
            },
            onExtensionsLoaded = {}
        )
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun restoreBrowserState() = GlobalScope.launch(Dispatchers.Main) {
        sessionStorage.get().let {
            tabsUseCases.get().restore(it)
            // Now that we have restored our previous state (if there's one) let's setup auto saving the state while the app is used.
            it.autoSave(store.get())
                .periodicallyInForeground(interval = 30, unit = TimeUnit.SECONDS)
                .whenGoingToBackground()
                .whenSessionsChange()
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        runOnlyInMainProcess {
            store.get().dispatch(SystemAction.LowMemoryAction(level))
        }
    }
}

private fun setupLogging() {
    // We want the log messages of all builds to go to Android logcat
    Log.addSink(AndroidLogSink())
    // RustLog.enable()
}