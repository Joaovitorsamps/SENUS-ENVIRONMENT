package com.jaax_sensus.data.repository

import com.jaax_sensus.data.DiaryEntry
import com.jaax_sensus.data.EmotionType
import com.jaax_sensus.data.remote.SupabaseClient
import com.jaax_sensus.data.remote.SupabaseConfig
import com.jaax_sensus.data.remote.dto.DiaryEntryDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DiaryRepository {

    private val localFallbackEntriesByUser = mutableMapOf<String, MutableList<DiaryEntry>>()

    suspend fun getEntries(): Result<List<DiaryEntry>> = withContext(Dispatchers.IO) {
        if (!SupabaseConfig.isConfigured) {
            return@withContext Result.success(fallbackEntriesForCurrentUser().toList())
        }

        return@withContext try {
            val userId = SupabaseConfig.currentUserId
            if (!isValidUuid(userId)) {
                return@withContext Result.failure(Exception("Usuário não autenticado."))
            }

            val dtoList = SupabaseClient.diaryApi.getEntries(
                select = "*",
                order = "Date_Time_Selection.desc",
                userFilter = "eq.$userId"
            )

            val domainEntries = dtoList.map { it.toDomain() }
            Result.success(domainEntries)
        } catch (e: Exception) {
            // Em caso de falha de conexão com a rede, retorna os dados locais
            val fallbackEntries = fallbackEntriesForCurrentUser()
            if (fallbackEntries.isNotEmpty()) {
                Result.success(fallbackEntries.toList())
            } else {
                Result.failure(e)
            }
        }
    }

    suspend fun addEntry(emotion: EmotionType, note: String): Result<DiaryEntry> = withContext(Dispatchers.IO) {
        val newEntry = DiaryEntry(
            emotion = emotion,
            note = note.trim(),
            timestamp = System.currentTimeMillis()
        )

        // Salva na lista local para suporte offline
        if (SupabaseConfig.isConfigured && !isValidUuid(SupabaseConfig.currentUserId)) {
            return@withContext Result.failure(Exception("Usuário não autenticado."))
        }
        fallbackEntriesForCurrentUser().add(0, newEntry)

        if (!SupabaseConfig.isConfigured) {
            return@withContext Result.success(newEntry)
        }

        return@withContext try {
            val dto = DiaryEntryDto.fromDomain(
                entry = newEntry,
                userId = SupabaseConfig.currentUserId
            )

            val responseList = SupabaseClient.diaryApi.insertEntry(
                userFilter = "eq.${SupabaseConfig.currentUserId}",
                entry = dto
            )
            val inserted = responseList.firstOrNull()?.toDomain() ?: newEntry
            Result.success(inserted)
        } catch (e: Exception) {
            Result.failure(Exception("Não foi possível salvar a emoção no Supabase: ${e.message}", e))
        }
    }

    suspend fun deleteEntry(id: String): Result<Unit> = withContext(Dispatchers.IO) {
        if (SupabaseConfig.isConfigured && !isValidUuid(SupabaseConfig.currentUserId)) {
            return@withContext Result.failure(Exception("Usuário não autenticado."))
        }
        fallbackEntriesForCurrentUser().removeAll { it.id == id }

        if (!SupabaseConfig.isConfigured) {
            return@withContext Result.success(Unit)
        }

        return@withContext try {
            val response = SupabaseClient.diaryApi.deleteEntry(
                idFilter = "eq.$id",
                userFilter = "eq.${SupabaseConfig.currentUserId}"
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Erro ao deletar no Supabase: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun isValidUuid(str: String?): Boolean {
        if (str.isNullOrBlank()) return false
        return try {
            java.util.UUID.fromString(str)
            true
        } catch (_: Exception) {
            false
        }
    }

    private fun fallbackEntriesForCurrentUser(): MutableList<DiaryEntry> {
        val userKey = SupabaseConfig.currentUserId
            ?: SupabaseConfig.currentUsername
            ?: "anonymous"
        return localFallbackEntriesByUser.getOrPut(userKey) { mutableListOf() }
    }
}
