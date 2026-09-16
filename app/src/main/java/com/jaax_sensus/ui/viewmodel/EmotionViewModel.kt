package com.jaax_sensus.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaax_sensus.data.DateFilter
import com.jaax_sensus.data.DiaryEntry
import com.jaax_sensus.data.EmotionType
import com.jaax_sensus.data.remote.SupabaseConfig
import com.jaax_sensus.data.repository.AuthRepository
import com.jaax_sensus.data.repository.DiaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class EmotionViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val diaryRepository: DiaryRepository = DiaryRepository()
) : ViewModel() {

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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val isBackendConnected: Boolean
        get() = SupabaseConfig.isConfigured

    fun login(username: String, password: String): Boolean {
        if (username.isBlank() || password.isBlank()) {
            _errorMessage.value = "Digite seu nome ou e-mail e sua senha."
            return false
        }
        val trimmed = username.trim()
        _userName.value = trimmed
        _isLoggedIn.value = false
        _errorMessage.value = null

        // Sincroniza em segundo plano com o Supabase se configurado
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.login(trimmed, password)
            _isLoading.value = false
            result.onSuccess { remoteUsername ->
                _userName.value = remoteUsername
                _isLoggedIn.value = true
                refreshEntries()
            }.onFailure { error ->
                _isLoggedIn.value = false
                _errorMessage.value = error.message
            }
        }

        return true
    }

    fun register(username: String, email: String, password: String): Boolean {
        val trimmedUsername = username.trim()
        val trimmedEmail = email.trim()
        if (trimmedUsername.isBlank() || trimmedEmail.isBlank() || password.isBlank()) {
            _errorMessage.value = "Preencha todos os campos."
            return false
        }

        _errorMessage.value = null
        _isLoading.value = true
        viewModelScope.launch {
            val result = authRepository.register(trimmedUsername, trimmedEmail, password)
            _isLoading.value = false
            result.onSuccess { registeredUsername ->
                _userName.value = registeredUsername
                _isLoggedIn.value = true
                refreshEntries()
            }.onFailure { error ->
                _errorMessage.value = error.message ?: "Não foi possível concluir o cadastro."
            }
        }
        return true
    }

    fun logout() {
        authRepository.logout()
        _isLoggedIn.value = false
        _entries.value = emptyList()
        _selectedEmotion.value = null
    }

    fun updateProfile(name: String, country: String, state: String, city: String) {
        if (name.isBlank()) {
            _errorMessage.value = "Informe seu nome de usuário."
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.updateProfile(name.trim(), country.trim(), state.trim(), city.trim())
            _isLoading.value = false
            result.onSuccess { updatedName ->
                _userName.value = updatedName
                _errorMessage.value = null
            }.onFailure { error ->
                _errorMessage.value = error.message
            }
        }
    }

    fun selectEmotion(emotion: EmotionType?) {
        _selectedEmotion.value = emotion
    }

    fun addEntry(emotion: EmotionType, note: String) {
        val trimmedNote = note.trim()
        val newEntry = DiaryEntry(
            emotion = emotion,
            note = trimmedNote,
            timestamp = System.currentTimeMillis()
        )
        // Atualização otimista imediata na UI
        _entries.value = listOf(newEntry) + _entries.value
        _selectedEmotion.value = null

        // Sincroniza com o Supabase
        val sessionUserId = SupabaseConfig.currentUserId
        viewModelScope.launch {
            val result = diaryRepository.addEntry(emotion, trimmedNote)
            if (SupabaseConfig.currentUserId != sessionUserId) return@launch
            result.onSuccess { inserted ->
                // Atualiza com o ID ou data oficial retornada pelo Supabase
                _entries.value = _entries.value.map {
                    if (it.id == newEntry.id) inserted else it
                }
            }.onFailure { error ->
                _entries.value = _entries.value.filterNot { it.id == newEntry.id }
                _errorMessage.value = error.message
            }
        }
    }

    fun deleteEntry(id: String) {
        _entries.value = _entries.value.filterNot { it.id == id }

        viewModelScope.launch {
            diaryRepository.deleteEntry(id)
        }
    }

    fun refreshEntries() {
        val sessionUserId = SupabaseConfig.currentUserId
        viewModelScope.launch {
            _isLoading.value = true
            val result = diaryRepository.getEntries()
            _isLoading.value = false
            if (SupabaseConfig.currentUserId != sessionUserId) return@launch
            result.onSuccess { remoteEntries ->
                _entries.value = remoteEntries
            }.onFailure { error ->
                _errorMessage.value = error.message
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
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
