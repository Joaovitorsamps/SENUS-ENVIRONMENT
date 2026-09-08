package com.jaax_sensus.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.MoodBad
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.filled.Spa
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

enum class EmotionType(
    val id: String,
    val title: String,
    val color: Color,
    val textColor: Color = Color.White,
    val icon: ImageVector
) {
    FELIZ(
        id = "1",
        title = "Feliz",
        color = Color(0xFF1B5E20), // green-900 / dark green
        textColor = Color(0xFFDCFCE7),
        icon = Icons.Default.SentimentVerySatisfied
    ),
    TRISTE(
        id = "2",
        title = "Triste",
        color = Color(0xFF1A237E), // indigo-900 / dark blue
        textColor = Color(0xFFDBEAFE),
        icon = Icons.Default.SentimentDissatisfied
    ),
    IRRITADO(
        id = "3",
        title = "Irritado",
        color = Color(0xFFB71C1C), // red-900 / dark red
        textColor = Color(0xFFFEE2E2),
        icon = Icons.Default.MoodBad
    ),
    ANSIOSO(
        id = "4",
        title = "Ansioso",
        color = Color(0xFF8D4F00), // amber/orange-900
        textColor = Color(0xFFFEF3C7),
        icon = Icons.Default.Bolt
    ),
    CANSADO(
        id = "5",
        title = "Cansado",
        color = Color(0xFF263238), // dark slate / gray-800
        textColor = Color(0xFFF3F4F6),
        icon = Icons.Default.Coffee
    ),
    CALMO(
        id = "6",
        title = "Calmo",
        color = Color(0xFF004D40), // teal-900 / dark teal
        textColor = Color(0xFFCCFBF1),
        icon = Icons.Default.Spa
    );

    companion object {
        fun fromId(id: String): EmotionType? = entries.find { it.id == id }
        fun fromTitle(title: String): EmotionType? = entries.find { it.title.equals(title, ignoreCase = true) }
    }
}

enum class DateFilter(val label: String) {
    HOJE("Hoje"),
    SETE_DIAS("7 Dias"),
    TRINTA_DIAS("30 Dias"),
    TODOS("Todos")
}

data class DiaryEntry(
    val id: String = UUID.randomUUID().toString(),
    val emotion: EmotionType,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
) {
    fun getFormattedDateTime(): String {
        val now = Calendar.getInstance()
        val entryCal = Calendar.getInstance().apply { timeInMillis = timestamp }

        val timeFormat = SimpleDateFormat("HH:mm", Locale.forLanguageTag("pt-BR"))
        val timeStr = timeFormat.format(Date(timestamp))

        val isToday = now.get(Calendar.YEAR) == entryCal.get(Calendar.YEAR) &&
                now.get(Calendar.DAY_OF_YEAR) == entryCal.get(Calendar.DAY_OF_YEAR)

        val yesterday = (now.clone() as Calendar).apply { add(Calendar.DAY_OF_YEAR, -1) }
        val isYesterday = yesterday.get(Calendar.YEAR) == entryCal.get(Calendar.YEAR) &&
                yesterday.get(Calendar.DAY_OF_YEAR) == entryCal.get(Calendar.DAY_OF_YEAR)

        return when {
            isToday -> "Hoje, $timeStr"
            isYesterday -> "Ontem, $timeStr"
            else -> {
                val dateFormat = SimpleDateFormat("dd MMM, HH:mm", Locale.forLanguageTag("pt-BR"))
                dateFormat.format(Date(timestamp))
            }
        }
    }
}
