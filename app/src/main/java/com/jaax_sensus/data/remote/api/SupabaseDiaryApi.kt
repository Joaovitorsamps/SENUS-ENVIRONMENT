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

    @GET("SENSUS_Emotion_Select")
    suspend fun getEntries(
        @Query("select") select: String = "*",
        @Query("order") order: String = "Date_Time_Selection.desc",
        @Query("User_ID") userFilter: String? = null
    ): List<DiaryEntryDto>

    @POST("SENSUS_Emotion_Select")
    suspend fun insertEntry(
        @Header("Prefer") prefer: String = "return=representation",
        @Body entry: DiaryEntryDto
    ): List<DiaryEntryDto>

    @DELETE("SENSUS_Emotion_Select")
    suspend fun deleteEntry(
        @Query("ID_Map") idFilter: String // e.g. "eq.uuid"
    ): Response<Unit>
}
