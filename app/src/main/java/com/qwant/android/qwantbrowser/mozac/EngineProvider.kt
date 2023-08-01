/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.qwant.android.qwantbrowser.mozac

import android.content.Context
import android.util.Log
import com.qwant.android.qwantbrowser.vip.QwantVIPFeature
import mozilla.components.browser.engine.gecko.GeckoEngine
import mozilla.components.browser.engine.gecko.fetch.GeckoViewFetchClient
import mozilla.components.concept.engine.DefaultSettings
import mozilla.components.concept.engine.Engine
import mozilla.components.concept.fetch.Client
import org.mozilla.geckoview.ContentBlocking
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoRuntimeSettings

object EngineProvider {
    private var runtime: GeckoRuntime? = null

    @Synchronized
    fun getOrCreateRuntime(context: Context): GeckoRuntime {
        if (runtime == null) {
            val builder = GeckoRuntimeSettings.Builder()

            // TODO explore runtime settings
            // TODO runtime builder.contentBlocking(...)
            // TODO set debug attribute of runtime relative to build type
            builder.aboutConfigEnabled(true)
            builder.consoleOutput(true)
            builder.debugLogging(true)
            builder.remoteDebuggingEnabled(true)

            runtime = GeckoRuntime.create(context, builder.build())
        }

        return runtime!!
    }

    fun createEngine(context: Context, defaultSettings: DefaultSettings): Engine {
        val runtime = getOrCreateRuntime(context)

        Log.d("QWANT_BROWSER_EXTENSION", "creating gecko engine")
        return GeckoEngine(context, defaultSettings, runtime).also {
            Log.d("QWANT_BROWSER_EXTENSION", "installing embed extensions")
            QwantVIPFeature.install(it)
            // WebCompatFeature.install(it)
        }
    }

    fun createClient(context: Context): Client {
        val runtime = getOrCreateRuntime(context)
        return GeckoViewFetchClient(context, runtime)
    }
}
