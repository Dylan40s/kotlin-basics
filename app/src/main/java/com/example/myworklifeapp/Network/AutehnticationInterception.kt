package com.example.myworklifeapp.Network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor(user: String, password: String) : Interceptor {

    private val credentials: String = Credentials.basic(user, password)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", credentials)
            .header("Content-Type", "application/json")
            .header("Accept", "application/vnd.myworklife-v1+json")
            //.url("https://bsg-staging.myworklife.com/app/rest/api/leave/createLeaveForm")
            .build()

        return chain.proceed(authenticatedRequest)
    }

}