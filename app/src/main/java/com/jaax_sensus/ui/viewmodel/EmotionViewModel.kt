package com.jaax_sensus.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.jaax_sensus.data.DateFilter
import com.jaax_sensus.data.DiaryEntry
import com.jaax_sensus.data.EmotionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EmotionViewModel : ViewModel() {

    private val _entries = MutableStateFlow<List<DiaryEntry>>(emptyList())
    val entries: StateFlow<List<DiaryEntry>> = _entries.asStateFlow()

    private val _selectedEmotion = MutableStateFlow<EmotionType?>(null)
    val selectedEmotion: StateFlow<EmotionType?> = _selectedEmotion.asStateFlow()

    private val _currentFilter = MutableStateFlow(DateFilter.SETE_DIAS)
    val currentFilter: StateFlow<DateFilter> = _currentFilter.asStateFlow()

    private val _filterStartDate = MutableStateFlow("")
    val filterStartDate: StateFlow<String> = _filterStartDate.asStateFlow()

    private val _filterEndDate = MutableStateFlow("")
    val filterEndDate: StateFlow<String> = _filterEndDate.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userName = MutableStateFlow("jose")
    val userName: StateFlow<String> = _userName.asStateFlow()

    fun login(username: String, password: String): Boolean {
        if (username.isBlank()) return false
        _userName.value = username.trim()
        _isLoggedIn.value = true
        return true
    }

    fun logout() {
        _isLoggedIn.value = false
    }

    fun selectEmotion(emotion: EmotionType?) {
        _selectedEmotion.value = emotion
    }

    fun addEntry(emotion: EmotionType, note: String) {
        val newEntry = DiaryEntry(
            emotion = emotion,
            note = note.trim(),
            timestamp = System.currentTimeMillis()
        )
        _entries.value = listOf(newEntry) + _entries.value
        _selectedEmotion.value = null
    }

    fun deleteEntry(id: String) {
        _entries.value = _entries.value.filterNot { it.id == id }
    }

    fun setFilter(filter: DateFilter) {
        _currentFilter.value = filter
    }

    fun setCustomDateRange(start: String, end: String) {
        _filterStartDate.value = start
        _filterEndDate.value = end
    }

    fun getFilteredEntries(): List<DiaryEntry> {
        val all = _entries.value
        val now = Calendar.getInstance()

        return when (_currentFilter.value) {
            DateFilter.HOJE -> {
                val startOfDay = (now.clone() as Calendar).apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis
                all.filter { it.timestamp >= startOfDay }
            }
            DateFilter.SETE_DIAS -> {
                val sevenDaysAgo = (now.clone() as Calendar).apply {
                    add(Calendar.DAY_OF_YEAR, -7)
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis
                all.filter { it.timestamp >= sevenDaysAgo }
            }
            DateFilter.TRINTA_DIAS -> {
                val thirtyDaysAgo = (now.clone() as Calendar).apply {
                    add(Calendar.DAY_OF_YEAR, -30)
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis
                all.filter { it.timestamp >= thirtyDaysAgo }
            }
            DateFilter.TODOS -> all
        }
    }

    fun getTopEmotion(): EmotionType? {
        val filtered = getFilteredEntries()
        if (filtered.isEmpty()) return null
        val counts = filtered.groupingBy { it.emotion }.eachCount()
        return counts.maxByOrNull { it.value }?.key
    }

    fun getEmotionFrequencies(): Map<EmotionType, Int> {
        val filtered = getFilteredEntries()
        return filtered.groupingBy { it.emotion }.eachCount()
    }
}

