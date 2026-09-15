package com.jaax_sensus.data.repository

import com.jaax_sensus.data.remote.SupabaseClient
import com.jaax_sensus.data.remote.SupabaseConfig
import com.jaax_sensus.data.remote.dto.SignInRequest
import com.jaax_sensus.data.remote.dto.SignUpRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {

    suspend fun login(loginInput: String, password: String): Result<String> = withContext(Dispatchers.IO) {
        val trimmed = loginInput.trim()
        if (trimmed.isBlank()) {
            return@withContext Result.failure(IllegalArgumentException("Nome de usuário ou e-mail não pode ser vazio."))
        }

        // Se o Supabase ainda não foi configurado pela equipe, entra no modo Demo/Offline
        if (!SupabaseConfig.isConfigured) {
            SupabaseConfig.currentUsername = trimmed
            SupabaseConfig.currentUserId = "demo-user-id"
            return@withContext Result.success(trimmed)
        }

        // Se for um username simples (ex: "jose"), mapeia para um email padrão ou usa diretamente
        val email = if (trimmed.contains("@")) trimmed else "$trimmed@sensus.app"

        return@withContext try {
            val response = SupabaseClient.authApi.signInWithPassword(
                SignInRequest(email = email, password = password)
            )

            if (response.isSuccessful && response.body()?.accessToken != null) {
                val body = response.body()!!
                SupabaseConfig.currentAccessToken = body.accessToken
                SupabaseConfig.currentUserId = body.user?.id
                val username = body.user?.userMetadata?.get("username") ?: trimmed
                SupabaseConfig.currentUsername = username
                Result.success(username)
            } else {
                val errorMsg = response.body()?.errorDescription
                    ?: response.body()?.msg
                    ?: "Falha na autenticação (${response.code()})"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(username: String, email: String, password: String): Result<String> = withContext(Dispatchers.IO) {
        if (!SupabaseConfig.isConfigured) {
            SupabaseConfig.currentUsername = username
            SupabaseConfig.currentUserId = "demo-user-id"
            return@withContext Result.success(username)
        }

        return@withContext try {
            val response = SupabaseClient.authApi.signUp(
                SignUpRequest(
                    email = email,
                    password = password,
                    data = mapOf("username" to username)
                )
            )

            if (response.isSuccessful) {
                val body = response.body()
                SupabaseConfig.currentAccessToken = body?.accessToken
                SupabaseConfig.currentUserId = body?.user?.id
                SupabaseConfig.currentUsername = username
                Result.success(username)
            } else {
                val errorMsg = response.body()?.errorDescription ?: "Falha no cadastro (${response.code()})"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        SupabaseConfig.currentAccessToken = null
        SupabaseConfig.currentUserId = null
        SupabaseConfig.currentUsername = null
    }
}
