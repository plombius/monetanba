package cz.moneta.monetanba.common.http

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor

class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        return runBlocking {
            val request = chain.request()

            val newRequest = request.newBuilder()
                .removeHeader("Authorization")
                .addHeader("Authorization", "4cf0bcf9-d2f8-4701-85cf-0956c2f3d937")
                .build()
            chain.proceed(newRequest)
        }
    }
}