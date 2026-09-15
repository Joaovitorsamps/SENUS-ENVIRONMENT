package com.jaax_sensus.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpRequest(
    val email: String,
    val password: String,
    val data: Map<String, String>? = null
)

@JsonClass(generateAdapter = true)
data class SignInRequest(
    val email: String,
    val password: String
)

@JsonClass(generateAdapter = true)
data class AuthResponse(
    @param:Json(name = "access_token") val accessToken: String? = null,
    @param:Json(name = "token_type") val tokenType: String? = null,
    @param:Json(name = "expires_in") val expiresIn: Long? = null,
    @param:Json(name = "refresh_token") val refreshToken: String? = null,
    val user: SupabaseUserDto? = null,
    @param:Json(name = "error_description") val errorDescription: String? = null,
    val msg: String? = null
)

@JsonClass(generateAdapter = true)
data class SupabaseUserDto(
    val id: String,
    val email: String? = null,
    @param:Json(name = "user_metadata") val userMetadata: Map<String, String>? = null
)
