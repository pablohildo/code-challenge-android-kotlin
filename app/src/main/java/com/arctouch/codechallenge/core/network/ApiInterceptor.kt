package com.arctouch.codechallenge.core.network

import com.arctouch.codechallenge.core.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

/** This interceptor guarantees that every request made will include the required by default `api_key`
 and the wanted `language`, avoiding the need to add those params to every api call
*/

class ApiInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val chainRequest = chain.request()
        val url = chainRequest
                .url()
                .newBuilder()
                .addQueryParameter("api_key", Constants.API_KEY)
                .addQueryParameter("language",
                        Constants.DEFAULT_LANGUAGE)
                .build()
        return chain.proceed(chainRequest.newBuilder().url(url).build())
    }
}