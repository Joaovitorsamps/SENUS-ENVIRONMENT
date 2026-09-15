package com.jaax_sensus.data.remote.api

import com.jaax_sensus.data.remote.dto.DiaryEntryDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SupabaseDiaryApi {

    @GET("diary_entries")
    suspend fun getEntries(
        @Query("select") select: String = "*",
        @Query("order") order: String = "created_at.desc",
        @Query("user_id") userFilter: String? = null
    ): List<DiaryEntryDto>

    @POST("diary_entries")
    suspend fun insertEntry(
        @Header("Prefer") prefer: String = "return=representation",
        @Body entry: DiaryEntryDto
    ): List<DiaryEntryDto>

    @DELETE("diary_entries")
    suspend fun deleteEntry(
        @Query("id") idFilter: String // e.g. "eq.uuid"
    ): Response<Unit>
}
