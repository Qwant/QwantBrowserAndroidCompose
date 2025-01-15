package com.qwant.android.qwantbrowser.contentBlocker

import android.util.Log
import com.qwant.android.qwantbrowser.contentBlocker.cacheDb.BlockListType
import com.qwant.android.qwantbrowser.contentBlocker.cacheDb.ContentBlockerCacheRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mozilla.components.concept.fetch.Client
import mozilla.components.concept.fetch.MutableHeaders
import mozilla.components.concept.fetch.Request
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentBlockerService @Inject constructor(
    private val cacheRepository: ContentBlockerCacheRepository,
    private val client: Client
) {
    private enum class Result {
        ALLOWED, BLOCKED, ERROR
    }

    suspend fun isDomainBlocked(hash: String): Boolean {
        val r = isDomainBlacklist(hash) || isDomainRedirect(hash)
        Log.d("QwantContentBlocker", "isDomainBlocked: $r")
        return r
    }

    suspend fun isUrlBLocked(hash: String): Boolean {
        val r = isUrlBlacklist(hash) || isUrlRedirect(hash)
        Log.d("QwantContentBlocker", "isUrlBLocked: $r")
        return r
    }

    private suspend fun isDomainBlacklist(hash: String): Boolean {
        cacheRepository.getDomain(hash, BlockListType.BLACKLIST)?.blocked?.let {
            Log.d("QwantContentBlocker", "isDomainBlacklist cached: $it")
            return it
        }

        Log.d("QwantContentBlocker", "isDomainBlacklist request")
        val result = executeRequest(getRequest(DOMAIN_BLACKLIST_ENDPOINT, hash))
        if (result != Result.ERROR) {
            val isBlocked = result == Result.BLOCKED
            cacheRepository.saveDomain(hash, isBlocked, BlockListType.BLACKLIST)
            Log.d("QwantContentBlocker", "isDomainBlacklist request result: $isBlocked")
            return isBlocked
        } else Log.d("QwantContentBlocker", "isDomainBlacklist request error")
        return false
    }

    private suspend fun isDomainRedirect(hash: String): Boolean {
        cacheRepository.getDomain(hash, BlockListType.REDIRECT)?.blocked?.let {
            Log.d("QwantContentBlocker", "isDomainRedirect cached: $it")
            return it
        }

        Log.d("QwantContentBlocker", "isDomainRedirect request")
        val result = this.executeRequest(this.getRequest(DOMAIN_REDIRECT_ENDPOINT, hash))
        if (result != Result.ERROR) {
            val isBlocked = result == Result.BLOCKED
            cacheRepository.saveDomain(hash, isBlocked, BlockListType.REDIRECT)
            Log.d("QwantContentBlocker", "isDomainRedirect request result: $isBlocked")
            return isBlocked
        } else Log.d("QwantContentBlocker", "isDomainRedirect request error")
        return false
    }

    private suspend fun isUrlBlacklist(hash: String): Boolean {
        cacheRepository.getUrl(hash, BlockListType.BLACKLIST)?.blocked?.let {
            Log.d("QwantContentBlocker", "isUrlBlacklist cached: $it")
            return it
        }

        Log.d("QwantContentBlocker", "isUrlBlacklist request")
        val result = executeRequest(getRequest(URL_BLACKLIST_ENDPOINT, hash))
        if (result != Result.ERROR) {
            val isBlocked = result == Result.BLOCKED
            cacheRepository.saveUrl(hash, isBlocked, BlockListType.BLACKLIST)
            Log.d("QwantContentBlocker", "isUrlBlacklist request result: $isBlocked")
            return isBlocked
        } else Log.d("QwantContentBlocker", "isUrlBlacklist request error")
        return false
    }

    private suspend fun isUrlRedirect(hash: String): Boolean {
        cacheRepository.getUrl(hash, BlockListType.REDIRECT)?.blocked?.let {
            Log.d("QwantContentBlocker", "isUrlRedirect cached: $it")
            return it
        }

        Log.d("QwantContentBlocker", "isUrlRedirect request")
        val result = this.executeRequest(this.getRequest(URL_REDIRECT_ENDPOINT, hash))
        if (result != Result.ERROR) {
            val isBlocked = result == Result.BLOCKED
            cacheRepository.saveUrl(hash, isBlocked, BlockListType.REDIRECT)
            Log.d("QwantContentBlocker", "isUrlRedirect request result: $isBlocked")
            return isBlocked
        } else Log.d("QwantContentBlocker", "isUrlRedirect request error")
        return false
    }

    private fun getRequest(endpoint: String, hash: String): Request {
        Log.d("QwantContentBlocker", "constructing request with\n    url: ${endpoint}\n    body: test1=$hash")
        return Request(
            url = endpoint,
            method = Request.Method.POST,
            headers = MutableHeaders(Pair("Content-Type", "application/x-www-form-urlencoded")),
            body = Request.Body.fromString("test1=$hash")
        )
    }

    private suspend fun executeRequest(request: Request): Result = withContext(Dispatchers.IO) {
        Log.d("QwantContentBlocker", "requesting $request\n    url: ${request.url}\n    body: ${request.body}")
        return@withContext try {
            client.fetch(request).use { response ->
                if (response.status == 200) {
                    response.body.use { body ->
                        val jsonResponse = JSONObject(body.string())
                        val result = jsonResponse.getBoolean("test1")
                        if (result) Result.BLOCKED else Result.ALLOWED
                    }
                } else {
                    Log.e("QwantContentBlocker", "Service Error Code: ${response.status}")
                    Result.ERROR
                }
            }
        } catch (e: JSONException) {
            Log.e("QwantContentBlocker", "Error parsing result", e)
            Result.ERROR
        } catch (e: IOException) {
            Log.e("QwantContentBlocker", "Error requesting service", e)
            Result.ERROR
        }
    }

    companion object {
        private const val HOST = "mobile-secure.qwantjunior.com"
        private const val BASE_URL = "https://$HOST/api/qwant-junior-mobile"
        private const val DOMAIN_BLACKLIST_ENDPOINT = "$BASE_URL/blacklist/domains/hash"
        private const val DOMAIN_REDIRECT_ENDPOINT = "$BASE_URL/redirect/domains/hash"
        private const val URL_BLACKLIST_ENDPOINT = "$BASE_URL/blacklist/urls/hash"
        private const val URL_REDIRECT_ENDPOINT = "$BASE_URL/redirect/urls/hash"
    }
}