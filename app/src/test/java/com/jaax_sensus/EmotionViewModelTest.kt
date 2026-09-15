package com.jaax_sensus

import com.jaax_sensus.data.DateFilter
import com.jaax_sensus.data.EmotionType
import com.jaax_sensus.ui.viewmodel.EmotionViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class EmotionViewModelTest {

    private lateinit var viewModel: EmotionViewModel

    @Before
    fun setUp() {
        viewModel = EmotionViewModel()
    }

    @Test
    fun initialState_hasEmptyEntriesAndDefaultFilter() {
        assertTrue(viewModel.entries.value.isEmpty())
        assertNull(viewModel.selectedEmotion.value)
        assertEquals(DateFilter.SETE_DIAS, viewModel.currentFilter.value)
        assertEquals("jose", viewModel.userName.value)
        org.junit.Assert.assertFalse(viewModel.isLoggedIn.value)
        assertNull(viewModel.getTopEmotion())
    }

    @Test
    fun login_withValidUsername_updatesIsLoggedInAndUserName() {
        val result = viewModel.login("Juliano", "123456")
        assertTrue(result)
        assertTrue(viewModel.isLoggedIn.value)
        assertEquals("Juliano", viewModel.userName.value)

        viewModel.logout()
        org.junit.Assert.assertFalse(viewModel.isLoggedIn.value)
    }

    @Test
    fun login_withBlankUsername_returnsFalse() {
        val result = viewModel.login("   ", "123456")
        org.junit.Assert.assertFalse(result)
        org.junit.Assert.assertFalse(viewModel.isLoggedIn.value)
    }

    @Test
    fun selectEmotion_updatesSelectedEmotionState() {
        viewModel.selectEmotion(EmotionType.FELIZ)
        assertEquals(EmotionType.FELIZ, viewModel.selectedEmotion.value)

        viewModel.selectEmotion(null)
        assertNull(viewModel.selectedEmotion.value)
    }

    @Test
    fun addEntry_addsNewEntryAndResetsSelectedEmotion() {
        viewModel.selectEmotion(EmotionType.ANSIOSO)
        viewModel.addEntry(EmotionType.ANSIOSO, "Sentindo ansiedade com a reunião")

        assertEquals(1, viewModel.entries.value.size)
        val entry = viewModel.entries.value.first()
        assertEquals(EmotionType.ANSIOSO, entry.emotion)
        assertEquals("Sentindo ansiedade com a reunião", entry.note)
        assertNull(viewModel.selectedEmotion.value)
    }

    @Test
    fun deleteEntry_removesTargetEntry() {
        viewModel.addEntry(EmotionType.FELIZ, "Dia excelente")
        viewModel.addEntry(EmotionType.CALMO, "Tarde tranquila")
        assertEquals(2, viewModel.entries.value.size)

        val idToDelete = viewModel.entries.value.first().id
        viewModel.deleteEntry(idToDelete)

        assertEquals(1, viewModel.entries.value.size)
        assertTrue(viewModel.entries.value.none { it.id == idToDelete })
    }

    @Test
    fun getTopEmotion_identifiesMostFrequentEmotion() {
        viewModel.addEntry(EmotionType.FELIZ, "1")
        viewModel.addEntry(EmotionType.FELIZ, "2")
        viewModel.addEntry(EmotionType.TRISTE, "3")

        val top = viewModel.getTopEmotion()
        assertNotNull(top)
        assertEquals(EmotionType.FELIZ, top)

        val frequencies = viewModel.getEmotionFrequencies()
        assertEquals(2, frequencies[EmotionType.FELIZ])
        assertEquals(1, frequencies[EmotionType.TRISTE])
    }

    @Test
    fun setFilter_updatesFilterState() {
        viewModel.setFilter(DateFilter.HOJE)
        assertEquals(DateFilter.HOJE, viewModel.currentFilter.value)

        viewModel.setFilter(DateFilter.TRINTA_DIAS)
        assertEquals(DateFilter.TRINTA_DIAS, viewModel.currentFilter.value)
    }

    @Test
    fun diaryEntryDto_convertsToAndFromDomainCorrectly() {
        val domain = com.jaax_sensus.data.DiaryEntry(
            id = "test-uuid",
            emotion = EmotionType.ANSIOSO,
            note = "Nota de teste",
            timestamp = 1700000000000L
        )

        val dto = com.jaax_sensus.data.remote.dto.DiaryEntryDto.fromDomain(domain, userId = "user-123")
        assertEquals("test-uuid", dto.id)
        assertEquals("user-123", dto.userId)
        assertEquals(EmotionType.ANSIOSO.id, dto.emotionId)
        assertEquals(EmotionType.ANSIOSO.title, dto.emotionName)
        assertEquals("Nota de teste", dto.note)
        assertNotNull(dto.createdAt)

        val convertedBack = dto.toDomain()
        assertEquals("test-uuid", convertedBack.id)
        assertEquals(EmotionType.ANSIOSO, convertedBack.emotion)
        assertEquals("Nota de teste", convertedBack.note)
    }

    @Test
    fun supabaseConfig_providesValidUrls() {
        assertTrue(com.jaax_sensus.data.remote.SupabaseConfig.getBaseRestUrl().endsWith("/rest/v1/"))
        assertTrue(com.jaax_sensus.data.remote.SupabaseConfig.getBaseAuthUrl().endsWith("/auth/v1/"))
    }
}

