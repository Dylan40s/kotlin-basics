package com.example.myworklifeapp.Network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

private const val url = "add url"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(Date::class.java, DateAsMillisecondsFromEpochJsonAdapter().nullSafe())
    .build()

private val okHttpClient = OkHttpClient().newBuilder()
    .addInterceptor(AuthenticationInterceptor("username", "password"))
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(url)
    .client(okHttpClient)
    .build()

interface BifrostApiService {
    @POST("rest/api/leave/createLeaveForm")
    fun createLeaveForm(@Body leaveForm: LeaveForm) : Deferred<Boolean>

}

object BifrostApi {
    val retrofitService : BifrostApiService by lazy {
        retrofit.create(BifrostApiService::class.java)
    }
}