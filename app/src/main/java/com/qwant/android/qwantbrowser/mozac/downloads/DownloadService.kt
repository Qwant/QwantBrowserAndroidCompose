/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.qwant.android.qwantbrowser.mozac.downloads

import dagger.hilt.android.AndroidEntryPoint
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.concept.fetch.Client
import mozilla.components.feature.downloads.AbstractFetchDownloadService
import mozilla.components.support.base.android.NotificationsDelegate
import javax.inject.Inject

@AndroidEntryPoint
class DownloadService : AbstractFetchDownloadService() {
    @Inject lateinit var c: dagger.Lazy<Client>
    override val httpClient: Client by lazy { c.get() }

    @Inject lateinit var s: dagger.Lazy<BrowserStore>
    override val store: BrowserStore by lazy { s.get() }

    @Inject lateinit var nd: dagger.Lazy<NotificationsDelegate>
    override val notificationsDelegate: NotificationsDelegate by lazy { nd.get() }
}
