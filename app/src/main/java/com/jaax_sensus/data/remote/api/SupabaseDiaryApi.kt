package com.jaax_sensus.data.remote.api

import com.jaax_sensus.data.remote.dto.DiaryEntryDto
import com.jaax_sensus.data.remote.dto.UserProfileDto
import com.jaax_sensus.data.remote.dto.UserProfileUpdateDto
import com.jaax_sensus.data.remote.dto.UsernameLookupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PATCH
import retrofit2.http.Query

interface SupabaseDiaryApi {

    @PATCH("Users")
    suspend fun updateUserProfile(
        @Header("Prefer") prefer: String = "return=minimal",
        @Query("User_ID") userFilter: String,
        @Body profile: UserProfileUpdateDto
    ): Response<Unit>

    @POST("rpc/get_email_by_username")
    suspend fun getEmailByUsername(
        @Body request: UsernameLookupRequest
    ): Response<String>

    @POST("Users")
    suspend fun insertUserProfile(
        @Header("Prefer") prefer: String = "resolution=ignore-duplicates,return=minimal",
        @Body profile: UserProfileDto
    ): Response<Unit>

    @GET("SENSUS_Emotion_Select")
    suspend fun getEntries(
        @Query("select") select: String = "*",
        @Query("order") order: String = "Date_Time_Selection.desc",
        @Query("User_ID") userFilter: String? = null
    ): List<DiaryEntryDto>

    @POST("SENSUS_Emotion_Select")
    suspend fun insertEntry(
        @Header("Prefer") prefer: String = "return=representation",
        @Query("User_ID") userFilter: String? = null,
        @Body entry: DiaryEntryDto
    ): List<DiaryEntryDto>

    @DELETE("SENSUS_Emotion_Select")
    suspend fun deleteEntry(
        @Query("ID_Map") idFilter: String,
        @Query("User_ID") userFilter: String
    ): Response<Unit>
}
