package com.jaax_sensus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaax_sensus.data.DiaryEntry
import com.jaax_sensus.data.EmotionType
import com.jaax_sensus.ui.components.SENSUSHeader
import com.jaax_sensus.ui.theme.DeepNavyBlue
import com.jaax_sensus.ui.theme.GreyishDarkBlue
import com.jaax_sensus.ui.theme.LightGrey
import com.jaax_sensus.ui.theme.PrimaryBlue
import com.jaax_sensus.ui.theme.White
import com.jaax_sensus.ui.viewmodel.EmotionViewModel

@Composable
fun DiarioScreen(
    viewModel: EmotionViewModel,
    onLogoutClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val entries by viewModel.entries.collectAsState()
    val selectedEmotion by viewModel.selectedEmotion.collectAsState()
    val userName by viewModel.userName.collectAsState()
    var noteText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DeepNavyBlue)
    ) {
        SENSUSHeader(
            userName = userName,
            onLogoutClick = onLogoutClick
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // "Novo Registro" Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = GreyishDarkBlue)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Card Header
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = null,
                                tint = PrimaryBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Novo Registro",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = White
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Emotion Picker label
                        Text(
                            text = "Qual emoção você sentiu?",
                            fontSize = 14.sp,
                            color = LightGrey
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Emotion Icons Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            EmotionType.entries.forEach { emotion ->
                                val isSelected = selectedEmotion == emotion
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isSelected) emotion.color else Color(0xFF243342)
                                        )
                                        .border(
                                            width = if (isSelected) 2.dp else 1.dp,
                                            color = if (isSelected) White else Color(0xFF33475B),
                                            shape = CircleShape
                                        )
                                        .clickable {
                                            viewModel.selectEmotion(
                                                if (isSelected) null else emotion
                                            )
                                            errorMessage = null
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = emotion.icon,
                                        contentDescription = emotion.title,
                                        tint = if (isSelected) emotion.textColor else LightGrey,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Situation label
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "O que aconteceu?",
                                fontSize = 14.sp,
                                color = LightGrey
                            )
                            Text(
                                text = "Toque nas tags abaixo:",
                                fontSize = 11.sp,
                                color = Color(0xFF64748B)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Creative Touch: Quick Context Chips for Neurodivergent Expression
                        val contextTags = listOf("Sensorial", "Barulho", "Mudança", "Sobrecarga", "Pausa", "Conquista")
                        androidx.compose.foundation.lazy.LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(bottom = 10.dp)
                        ) {
                            items(contextTags) { tag ->
                                val isTagAdded = noteText.contains(tag)
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(if (isTagAdded) PrimaryBlue.copy(alpha = 0.25f) else Color(0xFF243342))
                                        .border(
                                            width = 1.dp,
                                            color = if (isTagAdded) PrimaryBlue else Color(0xFF33475B),
                                            shape = RoundedCornerShape(20.dp)
                                        )
                                        .clickable {
                                            if (!isTagAdded) {
                                                noteText = if (noteText.isBlank()) tag else "$noteText • $tag"
                                            }
                                        }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "+ $tag",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (isTagAdded) PrimaryBlue else LightGrey
                                    )
                                }
                            }
                        }

                        // Note text field
                        OutlinedTextField(
                            value = noteText,
                            onValueChange = {
                                noteText = it
                                errorMessage = null
                            },
                            placeholder = {
                                Text(
                                    text = "Descreva a situação...",
                                    color = Color(0xFF64748B),
                                    fontSize = 14.sp
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 96.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFF243342),
                                unfocusedContainerColor = Color(0xFF243342),
                                focusedBorderColor = PrimaryBlue,
                                unfocusedBorderColor = Color(0xFF33475B),
                                focusedTextColor = White,
                                unfocusedTextColor = White
                            )
                        )

                        if (errorMessage != null) {
                            Text(
                                text = errorMessage!!,
                                color = Color(0xFFEF4444),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Save button
                        Button(
                            onClick = {
                                if (selectedEmotion == null) {
                                    errorMessage = "Por favor, selecione uma emoção antes de salvar."
                                } else {
                                    viewModel.addEntry(selectedEmotion!!, noteText)
                                    noteText = ""
                                    errorMessage = null
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryBlue,
                                contentColor = White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Salvar no Diário",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // "Histórico" Section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Histórico",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (entries.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 36.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nenhum registro ainda.",
                            color = LightGrey,
                            fontSize = 15.sp
                        )
                    }
                }
            } else {
                items(entries, key = { it.id }) { entry ->
                    DiaryEntryCard(
                        entry = entry,
                        onDeleteClick = { viewModel.deleteEntry(entry.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun DiaryEntryCard(
    entry: DiaryEntry,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GreyishDarkBlue),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2A3D52))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left color accent bar for instant emotional visual identification
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height(68.dp)
                    .background(entry.emotion.color)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Emotion Icon Circle
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(entry.emotion.color),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = entry.emotion.icon,
                        contentDescription = entry.emotion.title,
                        tint = entry.emotion.textColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

            Spacer(modifier = Modifier.width(14.dp))

            // Text Info
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = entry.emotion.title,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = White
                    )
                    Text(
                        text = entry.getFormattedDateTime(),
                        color = Color(0xFF8E8E93),
                        fontSize = 12.sp
                    )
                }

                if (entry.note.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = entry.note,
                        fontSize = 13.sp,
                        color = LightGrey
                    )
                }
            }

            // Delete button
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = "Excluir registro",
                    tint = Color(0xFF64748B),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
}



