package com.lucasbais.paymentdemo.datasource.network

import okhttp3.Interceptor
import okhttp3.Response


class PublicKeyInterceptor(private val publicKeyValue: String) : Interceptor {

    companion object {
        private const val QUERY_KEY = "public_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()
        val url = originalHttpUrl.newBuilder()
                .addQueryParameter(QUERY_KEY, publicKeyValue)
                .build()

        val requestBuilder = original.newBuilder()
                .url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}