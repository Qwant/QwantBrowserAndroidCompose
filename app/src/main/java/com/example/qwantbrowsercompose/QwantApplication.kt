package com.example.qwantbrowsercompose

import android.app.Application
import com.example.qwantbrowsercompose.mozac.Core
import com.example.qwantbrowsercompose.mozac.UseCases
import com.example.qwantbrowsercompose.usecases.QwantUseCases
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import mozilla.components.browser.state.action.SystemAction
import mozilla.components.support.base.log.Log
import mozilla.components.support.base.log.sink.AndroidLogSink
import mozilla.components.support.ktx.android.content.isMainProcess
import mozilla.components.support.ktx.android.content.runOnlyInMainProcess
import mozilla.components.support.rusthttp.RustHttpConfig
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
        restoreBrowserState()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun restoreBrowserState() = GlobalScope.launch(Dispatchers.Main) {
        useCases.tabsUseCases.restore(mozac.sessionStorage).invokeOnCompletion {
            if (mozac.store.state.tabs.isEmpty()) {
                useCases.tabsUseCases.addTab("http://www.qwant.com", selectTab = true)
            }
        }

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