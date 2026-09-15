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
    val id: String? = null,
    @param:Json(name = "user_id") val userId: String? = null,
    @param:Json(name = "emotion_id") val emotionId: String,
    @param:Json(name = "emotion_name") val emotionName: String,
    val note: String = "",
    @param:Json(name = "created_at") val createdAt: String? = null
) {
    fun toDomain(): DiaryEntry {
        val emotion = EmotionType.fromId(emotionId)
            ?: EmotionType.fromTitle(emotionName)
            ?: EmotionType.FELIZ

        val parsedTimestamp = parseIsoTimestamp(createdAt)

        return DiaryEntry(
            id = id ?: java.util.UUID.randomUUID().toString(),
            emotion = emotion,
            note = note,
            timestamp = parsedTimestamp
        )
    }

    companion object {
        fun fromDomain(entry: DiaryEntry, userId: String?): DiaryEntryDto {
            return DiaryEntryDto(
                id = entry.id,
                userId = userId,
                emotionId = entry.emotion.id,
                emotionName = entry.emotion.title,
                note = entry.note,
                createdAt = formatIsoTimestamp(entry.timestamp)
            )
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
