package com.jaax_sensus.data.remote.api

import com.jaax_sensus.data.remote.dto.AuthResponse
import com.jaax_sensus.data.remote.dto.SignInRequest
import com.jaax_sensus.data.remote.dto.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SupabaseAuthApi {

    @POST("signup")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): Response<AuthResponse>

    @POST("token?grant_type=password")
    suspend fun signInWithPassword(
        @Body request: SignInRequest
    ): Response<AuthResponse>

    @POST("logout")
    suspend fun logout(): Response<Unit>
}
