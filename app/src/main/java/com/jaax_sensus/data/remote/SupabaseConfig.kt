package com.jaax_sensus.data.remote

import com.jaax_sensus.BuildConfig

/**
 * Configurações de conexão com o Supabase.
 * As chaves são lidas de local.properties (supabase.url e supabase.anon.key) via BuildConfig,
 * ou podem ser substituídas em tempo de execução.
 */
object SupabaseConfig {

    var url: String = BuildConfig.SUPABASE_URL.ifBlank { "https://sua-url-supabase.supabase.co" }
    var anonKey: String = BuildConfig.SUPABASE_ANON_KEY.ifBlank { "sua-chave-anonima" }

    var currentAccessToken: String? = null
    var currentUserId: String? = null
    var currentUsername: String? = null

    /**
     * Retorna true se a URL e a Anon Key foram preenchidas com valores válidos.
     */
    val isConfigured: Boolean
        get() = url.isNotBlank() &&
                !url.contains("sua-url-supabase") &&
                !url.contains("placeholder") &&
                anonKey.isNotBlank() &&
                !anonKey.contains("sua-chave")

    fun getBaseRestUrl(): String {
        val trimmed = url.trim().trimEnd('/')
        return "$trimmed/rest/v1/"
    }

    fun getBaseAuthUrl(): String {
        val trimmed = url.trim().trimEnd('/')
        return "$trimmed/auth/v1/"
    }
}
