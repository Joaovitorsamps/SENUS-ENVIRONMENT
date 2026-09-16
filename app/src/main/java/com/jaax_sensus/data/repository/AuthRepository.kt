package com.jaax_sensus.data.repository

import com.jaax_sensus.data.remote.SupabaseClient
import com.jaax_sensus.data.remote.SupabaseConfig
import com.jaax_sensus.data.remote.dto.SignInRequest
import com.jaax_sensus.data.remote.dto.SignUpRequest
import com.jaax_sensus.data.remote.dto.UserProfileDto
import com.jaax_sensus.data.remote.dto.UserProfileUpdateDto
import com.jaax_sensus.data.remote.dto.UsernameLookupRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {

    suspend fun login(loginInput: String, password: String): Result<String> = withContext(Dispatchers.IO) {
        val trimmed = loginInput.trim()
        if (trimmed.isBlank()) {
            return@withContext Result.failure(IllegalArgumentException("Digite o e-mail cadastrado."))
        }
        if (trimmed.equals("demo_user", ignoreCase = true) && password == "demo_password") {
            SupabaseConfig.isDemoMode = true
            SupabaseConfig.currentUsername = "demo_user"
            SupabaseConfig.currentUserId = demoUserId("demo_user")
            SupabaseConfig.currentAccessToken = null
            return@withContext Result.success("demo_user")
        }
        SupabaseConfig.isDemoMode = false

        // Se o Supabase ainda não foi configurado pela equipe, entra no modo Demo/Offline
        if (!SupabaseConfig.isConfigured) {
            SupabaseConfig.isDemoMode = true
            SupabaseConfig.currentUsername = trimmed
            SupabaseConfig.currentUserId = demoUserId(trimmed)
            return@withContext Result.success(trimmed)
        }

        return@withContext try {
            SupabaseConfig.isDemoMode = false
            val email = if (trimmed.contains("@")) {
                trimmed
            } else {
                val lookupResponse = SupabaseClient.diaryApi.getEmailByUsername(
                    UsernameLookupRequest(username_input = trimmed)
                )
                if (!lookupResponse.isSuccessful) {
                    return@withContext Result.failure(Exception("Conta não encontrada."))
                }
                lookupResponse.body()?.trim()?.takeIf { it.isNotBlank() }
                    ?: return@withContext Result.failure(Exception("Conta não encontrada."))
            }
            val response = SupabaseClient.authApi.signInWithPassword(
                SignInRequest(email = email, password = password)
            )

            if (response.isSuccessful && response.body()?.accessToken != null) {
                val body = response.body()!!
                SupabaseConfig.currentAccessToken = body.accessToken
                SupabaseConfig.currentUserId = body.user?.id
                val username = body.user?.userMetadata?.username ?: trimmed
                SupabaseConfig.currentUsername = username
                ensureUserProfile(username)
                Result.success(username)
            } else {
                val errorBody = SupabaseClient.parseAuthError(response.errorBody()?.string())
                val errorMsg = when {
                    !errorBody?.errorDescription.isNullOrBlank() -> errorBody.errorDescription
                    !errorBody?.msg.isNullOrBlank() -> errorBody.msg
                    response.code() == 400 -> "Conta não encontrada ou senha incorreta."
                    else -> "Falha na autenticação (${response.code()})"
                }
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(username: String, email: String, password: String): Result<String> = withContext(Dispatchers.IO) {
        SupabaseConfig.isDemoMode = false
        if (!SupabaseConfig.isConfigured) {
            SupabaseConfig.isDemoMode = true
            SupabaseConfig.currentUsername = username
            SupabaseConfig.currentUserId = demoUserId(username)
            return@withContext Result.success(username)
        }

        return@withContext try {
            SupabaseConfig.isDemoMode = false
            val response = SupabaseClient.authApi.signUp(
                SignUpRequest(
                    email = email,
                    password = password,
                    data = mapOf("username" to username)
                )
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body?.user == null) {
                    return@withContext Result.failure(Exception("O cadastro não retornou um usuário válido."))
                }
                SupabaseConfig.currentAccessToken = body?.accessToken
                SupabaseConfig.currentUserId = body?.user?.id
                SupabaseConfig.currentUsername = username
                ensureUserProfile(username)
                Result.success(username)
            } else {
                val errorBody = SupabaseClient.parseAuthError(response.errorBody()?.string())
                val errorMsg = when {
                    response.code() == 429 -> "Limite de cadastros atingido. Aguarde alguns minutos e tente novamente."
                    !errorBody?.errorDescription.isNullOrBlank() -> errorBody.errorDescription
                    !errorBody?.msg.isNullOrBlank() -> errorBody.msg
                    else -> "Falha no cadastro (${response.code()})"
                }
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        SupabaseConfig.isDemoMode = false
        SupabaseConfig.currentAccessToken = null
        SupabaseConfig.currentUserId = null
        SupabaseConfig.currentUsername = null
    }

    suspend fun updateProfile(
        name: String,
        country: String,
        state: String,
        city: String
    ): Result<String> = withContext(Dispatchers.IO) {
        val userId = SupabaseConfig.currentUserId
        if (userId.isNullOrBlank() || !isValidUuid(userId)) {
            return@withContext Result.failure(Exception("Usuário não autenticado."))
        }

        if (!SupabaseConfig.isConfigured) {
            SupabaseConfig.currentUsername = name
            return@withContext Result.success(name)
        }

        return@withContext try {
            val response = SupabaseClient.diaryApi.updateUserProfile(
                userFilter = "eq.$userId",
                profile = UserProfileUpdateDto(name, country, state, city)
            )
            if (response.isSuccessful) {
                SupabaseConfig.currentUsername = name
                Result.success(name)
            } else {
                Result.failure(Exception("Erro no banco de dados (${response.code()})."))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Não foi possível atualizar o perfil."))
        }
    }

    private suspend fun ensureUserProfile(username: String) {
        val userId = SupabaseConfig.currentUserId ?: return
        if (!isValidUuid(userId)) return

        val response = SupabaseClient.diaryApi.insertUserProfile(
            profile = UserProfileDto(userId = userId, name = username)
        )
        if (!response.isSuccessful && response.code() != 409) {
            throw IllegalStateException(
                "Erro no banco de dados (${response.code()})."
            )
        }
    }

    private fun isValidUuid(value: String): Boolean = runCatching {
        java.util.UUID.fromString(value)
    }.isSuccess

    private fun demoUserId(username: String): String =
        java.util.UUID.nameUUIDFromBytes("demo:$username".toByteArray()).toString()
}
