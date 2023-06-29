/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.qwant.android.qwantbrowser.mozac.downloads

import com.qwant.android.qwantbrowser.ext.core
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.feature.downloads.AbstractFetchDownloadService

class DownloadService : AbstractFetchDownloadService() {
    override val httpClient by lazy { core.client }
    override val store: BrowserStore by lazy { core.store }
}
