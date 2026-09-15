package com.jaax_sensus.data.remote.dto

import com.jaax_sensus.data.DiaryEntry
import com.jaax_sensus.data.EmotionType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@JsonClass(generateAdapter = true)
data class DiaryEntryDto(
    @param:Json(name = "ID_Map") val id: String? = null,
    @param:Json(name = "User_ID") val userId: String? = null,
    @param:Json(name = "Selection_Type") val selectionType: String,
    @param:Json(name = "Date_Time_Selection") val dateTimeSelection: String? = null
) {
    fun toDomain(): DiaryEntry {
        val emotion = EmotionType.fromTitle(selectionType)
            ?: EmotionType.fromId(selectionType)
            ?: EmotionType.FELIZ

        val parsedTimestamp = parseIsoTimestamp(dateTimeSelection)

        return DiaryEntry(
            id = id ?: java.util.UUID.randomUUID().toString(),
            emotion = emotion,
            note = "",
            timestamp = parsedTimestamp
        )
    }

    companion object {
        fun fromDomain(entry: DiaryEntry, userId: String?): DiaryEntryDto {
            return DiaryEntryDto(
                id = if (isValidUuid(entry.id)) entry.id else java.util.UUID.randomUUID().toString(),
                userId = if (isValidUuid(userId)) userId else null,
                selectionType = entry.emotion.title,
                dateTimeSelection = formatIsoTimestamp(entry.timestamp)
            )
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

        private fun parseIsoTimestamp(isoString: String?): Long {
            if (isoString.isNullOrBlank()) return System.currentTimeMillis()
            return try {
                // Tenta formatos ISO comuns vindos do Postgres/Supabase
                val formats = listOf(
                    "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX",
                    "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
                    "yyyy-MM-dd'T'HH:mm:ssXXX",
                    "yyyy-MM-dd'T'HH:mm:ss"
                )
                for (pattern in formats) {
                    try {
                        val sdf = SimpleDateFormat(pattern, Locale.US)
                        val parsed = sdf.parse(isoString)
                        if (parsed != null) return parsed.time
                    } catch (_: Exception) { }
                }
                System.currentTimeMillis()
            } catch (_: Exception) {
                System.currentTimeMillis()
            }
        }

        private fun formatIsoTimestamp(millis: Long): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            return sdf.format(Date(millis))
        }
    }
}
