package com.jaax_sensus.data.remote

import com.jaax_sensus.data.remote.api.SupabaseAuthApi
import com.jaax_sensus.data.remote.api.SupabaseDiaryApi
import com.jaax_sensus.data.remote.dto.AuthResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object SupabaseClient {

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    fun parseAuthError(body: String?): AuthResponse? {
        if (body.isNullOrBlank()) return null
        return runCatching {
            moshi.adapter(AuthResponse::class.java).fromJson(body)
        }.getOrNull()
    }

    private val authHeaderInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("apikey", SupabaseConfig.anonKey)
            .header("Content-Type", "application/json")

        val token = SupabaseConfig.currentAccessToken
        if (!token.isNullOrBlank()) {
            requestBuilder.header("Authorization", "Bearer $token")
        } else {
            requestBuilder.header("Authorization", "Bearer ${SupabaseConfig.anonKey}")
        }

        chain.proceed(requestBuilder.build())
    }

    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authHeaderInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    val authApi: SupabaseAuthApi by lazy {
        Retrofit.Builder()
            .baseUrl(SupabaseConfig.getBaseAuthUrl())
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SupabaseAuthApi::class.java)
    }

    val diaryApi: SupabaseDiaryApi by lazy {
        Retrofit.Builder()
            .baseUrl(SupabaseConfig.getBaseRestUrl())
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SupabaseDiaryApi::class.java)
    }
}
