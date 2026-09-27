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
import androidx.compose.foundation.lazy.LazyRow
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
import com.jaax_sensus.ui.theme.SensusCreamDark
import com.jaax_sensus.ui.theme.SensusCreamLight
import com.jaax_sensus.ui.theme.SensusDarkTaupe
import com.jaax_sensus.ui.theme.SensusMintGreen
import com.jaax_sensus.ui.theme.SensusSageTeal
import com.jaax_sensus.ui.theme.SensusTaupeDark
import com.jaax_sensus.ui.theme.SensusTaupeMuted
import com.jaax_sensus.ui.theme.SensusTerracotta
import com.jaax_sensus.ui.theme.SensusWarmCream
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
            .background(SensusSageTeal)
    ) {
        SENSUSHeader(
            userName = userName,
            onLogoutClick = onLogoutClick
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            // "Novo Registro" Card - Estilo Warm Cream
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = SensusWarmCream),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SensusCreamDark),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        // Card Header
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = null,
                                tint = SensusTerracotta,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Novo Registro no Diário",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = SensusDarkTaupe
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Qual emoção você sentiu?",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SensusDarkTaupe
                        )

                        Spacer(modifier = Modifier.height(10.dp))

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
                                        .size(46.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isSelected) emotion.color else SensusCreamLight
                                        )
                                        .border(
                                            width = if (isSelected) 2.5.dp else 1.dp,
                                            color = if (isSelected) SensusTerracotta else SensusCreamDark,
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
                                        tint = if (isSelected) emotion.textColor else SensusDarkTaupe,
                                        modifier = Modifier.size(24.dp)
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
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SensusDarkTaupe
                            )
                            Text(
                                text = "Toque nas tags:",
                                fontSize = 11.sp,
                                color = SensusTaupeMuted
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Quick Context Chips
                        val contextTags = listOf("Sensorial", "Barulho", "Mudança", "Sobrecarga", "Pausa", "Conquista")
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(bottom = 12.dp)
                        ) {
                            items(contextTags) { tag ->
                                val isTagAdded = noteText.contains(tag)
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(if (isTagAdded) SensusMintGreen else SensusCreamLight)
                                        .border(
                                            width = 1.dp,
                                            color = if (isTagAdded) SensusDarkTaupe else SensusCreamDark,
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .clickable {
                                            if (!isTagAdded) {
                                                noteText = if (noteText.isBlank()) tag else "$noteText • $tag"
                                            }
                                        }
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "+ $tag",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = SensusDarkTaupe
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
                                    text = "Descreva a situação com suas palavras...",
                                    color = SensusTaupeMuted,
                                    fontSize = 14.sp
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 96.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = SensusCreamLight,
                                unfocusedContainerColor = SensusCreamLight,
                                focusedBorderColor = SensusTerracotta,
                                unfocusedBorderColor = SensusCreamDark,
                                focusedTextColor = SensusTaupeDark,
                                unfocusedTextColor = SensusTaupeDark
                            )
                        )

                        if (errorMessage != null) {
                            Text(
                                text = errorMessage!!,
                                color = Color(0xFFC0392B),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Save button - Terracotta Pill
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
                                .height(50.dp),
                            shape = RoundedCornerShape(25.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SensusTerracotta,
                                contentColor = SensusWarmCream
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SensusWarmCream,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Salvar no Diário",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = SensusWarmCream
                            )
                        }
                    }
                }
            }

            // "Histórico" Section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Histórico de Registros",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = SensusDarkTaupe
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
                            color = SensusDarkTaupe.copy(alpha = 0.8f),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
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
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SensusWarmCream),
        border = androidx.compose.foundation.BorderStroke(1.dp, SensusCreamDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Barra lateral da cor da emoção
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .height(72.dp)
                    .background(entry.emotion.color)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
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
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = SensusDarkTaupe
                        )
                        Text(
                            text = entry.getFormattedDateTime(),
                            color = SensusTaupeMuted,
                            fontSize = 11.sp
                        )
                    }

                    if (entry.note.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = entry.note,
                            fontSize = 13.sp,
                            color = SensusTaupeDark,
                            lineHeight = 17.sp
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
                        tint = SensusTaupeMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
