package com.qwant.android.qwantbrowser

import android.app.Application
import com.qwant.android.qwantbrowser.mozac.Core
import com.qwant.android.qwantbrowser.mozac.UseCases
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import mozilla.components.browser.state.action.SystemAction
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
    @Inject lateinit var mozac: Core
    @Inject lateinit var useCases: UseCases

    override fun onCreate() {
        super.onCreate()

        RustHttpConfig.setClient(lazy { mozac.client })
        setupLogging()

        if (!isMainProcess()) {
            // If this is not the main process then do not continue with the initialization here. Everything that
            // follows only needs to be done in our app's main process and should not be done in other processes like
            // a GeckoView child process or the crash handling process. Most importantly we never want to end up in a
            // situation where we create a GeckoRuntime from the Gecko child process (
            return
        }

        mozac.engine.warmUp()
        mozac.engine.speculativeConnect("https://www.qwant.com/")
        useCases.qwantUseCases.warmUp()

        restoreBrowserState()

        // Should be removed in futur version, once mozilla has fully migrated
        WebExtensionSupport.initialize(
            runtime = mozac.engine,
            store = mozac.store,
            openPopupInTab = false,
            onNewTabOverride = { _, engineSession, url ->
                val tabId = useCases.tabsUseCases.addTab(
                    url = url,
                    selectTab = true,
                    engineSession = engineSession
                )
                tabId
            },
            onCloseTabOverride = { _, sessionId ->
                useCases.tabsUseCases.removeTab(sessionId)
            },
            onSelectTabOverride = { _, sessionId ->
                useCases.tabsUseCases.selectTab(sessionId)
            },
            onExtensionsLoaded = {}
        )
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun restoreBrowserState() = GlobalScope.launch(Dispatchers.Main) {
        useCases.tabsUseCases.restore(mozac.sessionStorage)/* .invokeOnCompletion {
            if (mozac.store.state.tabs.isEmpty()) {
                useCases.qwantUseCases.openQwantPage()
            }
        } */

        // Now that we have restored our previous state (if there's one) let's setup auto saving the state while the app is used.
        mozac.sessionStorage.autoSave(mozac.store)
            .periodicallyInForeground(interval = 30, unit = TimeUnit.SECONDS)
            .whenGoingToBackground()
            .whenSessionsChange()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        runOnlyInMainProcess {
            mozac.store.dispatch(SystemAction.LowMemoryAction(level))
        }
    }
}

private fun setupLogging() {
    // We want the log messages of all builds to go to Android logcat
    Log.addSink(AndroidLogSink())
    // RustLog.enable()
}