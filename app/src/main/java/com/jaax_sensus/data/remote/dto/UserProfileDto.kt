package com.jaax_sensus.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@JsonClass(generateAdapter = true)
data class UserProfileDto(
    @param:Json(name = "User_ID") val userId: String,
    @param:Json(name = "Name") val name: String,
    @param:Json(name = "Birth_Date") val birthDate: String = "\\x",
    @param:Json(name = "Country") val country: String = "null",
    @param:Json(name = "State") val state: String = "null",
    @param:Json(name = "City") val city: String = "null",
    @param:Json(name = "status") val status: String = "active",
    @param:Json(name = "Created_At") val createdAt: String = currentUtcTimestamp()
)

private fun currentUtcTimestamp(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US)
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(Date())
}
