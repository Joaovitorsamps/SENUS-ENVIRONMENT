package com.jaax_sensus.data.repository

import com.jaax_sensus.data.DiaryEntry
import com.jaax_sensus.data.EmotionType
import com.jaax_sensus.data.remote.SupabaseClient
import com.jaax_sensus.data.remote.SupabaseConfig
import com.jaax_sensus.data.remote.dto.DiaryEntryDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DiaryRepository {

    private val localFallbackEntries = mutableListOf<DiaryEntry>()

    suspend fun getEntries(): Result<List<DiaryEntry>> = withContext(Dispatchers.IO) {
        if (!SupabaseConfig.isConfigured) {
            return@withContext Result.success(localFallbackEntries.toList())
        }

        return@withContext try {
            val userId = SupabaseConfig.currentUserId
            val userFilter = if (!userId.isNullOrBlank() && userId != "demo-user-id") "eq.$userId" else null

            val dtoList = SupabaseClient.diaryApi.getEntries(
                select = "*",
                order = "created_at.desc",
                userFilter = userFilter
            )

            val domainEntries = dtoList.map { it.toDomain() }
            Result.success(domainEntries)
        } catch (e: Exception) {
            // Em caso de falha de conexão com a rede, retorna os dados locais
            if (localFallbackEntries.isNotEmpty()) {
                Result.success(localFallbackEntries.toList())
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
        localFallbackEntries.add(0, newEntry)

        if (!SupabaseConfig.isConfigured) {
            return@withContext Result.success(newEntry)
        }

        return@withContext try {
            val dto = DiaryEntryDto.fromDomain(
                entry = newEntry,
                userId = SupabaseConfig.currentUserId
            )

            val responseList = SupabaseClient.diaryApi.insertEntry(entry = dto)
            val inserted = responseList.firstOrNull()?.toDomain() ?: newEntry
            Result.success(inserted)
        } catch (e: Exception) {
            // Se falhou ao enviar pro Supabase, ainda mantemos localmente e reportamos sucesso com fallback
            Result.success(newEntry)
        }
    }

    suspend fun deleteEntry(id: String): Result<Unit> = withContext(Dispatchers.IO) {
        localFallbackEntries.removeAll { it.id == id }

        if (!SupabaseConfig.isConfigured) {
            return@withContext Result.success(Unit)
        }

        return@withContext try {
            val response = SupabaseClient.diaryApi.deleteEntry(idFilter = "eq.$id")
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Erro ao deletar no Supabase: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
