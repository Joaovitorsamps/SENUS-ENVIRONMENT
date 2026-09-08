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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaax_sensus.data.DateFilter
import com.jaax_sensus.data.EmotionType
import com.jaax_sensus.ui.components.TechTEAHeader
import com.jaax_sensus.ui.theme.DeepNavyBlue
import com.jaax_sensus.ui.theme.GreyishDarkBlue
import com.jaax_sensus.ui.theme.LightGrey
import com.jaax_sensus.ui.theme.PrimaryBlue
import com.jaax_sensus.ui.theme.White
import com.jaax_sensus.ui.viewmodel.EmotionViewModel

@Composable
fun DadosScreen(
    viewModel: EmotionViewModel,
    onNavigateToEmocoes: () -> Unit,
    onLogoutClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val currentFilter by viewModel.currentFilter.collectAsState()
    val entries by viewModel.entries.collectAsState()
    val userName by viewModel.userName.collectAsState()

    val filteredEntries = viewModel.getFilteredEntries()
    val totalCount = filteredEntries.size
    val topEmotion = viewModel.getTopEmotion()
    val frequencies = viewModel.getEmotionFrequencies()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DeepNavyBlue)
    ) {
        TechTEAHeader(
            userName = userName,
            onLogoutClick = onLogoutClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top 3 Metric Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Card 1: Registros
                MetricSummaryCard(
                    title = "REGISTROS",
                    value = totalCount.toString(),
                    icon = Icons.Default.Adjust,
                    gradientColors = listOf(Color(0xFF1E3A8A), Color(0xFF2563EB)),
                    iconTint = Color(0xFF60A5FA),
                    modifier = Modifier.weight(1f)
                )

                // Card 2: Top Emoção
                MetricSummaryCard(
                    title = "TOP EMOÇÃO",
                    value = topEmotion?.title ?: "-",
                    icon = Icons.AutoMirrored.Filled.TrendingUp,
                    gradientColors = listOf(Color(0xFF581C87), Color(0xFF7C3AED)),
                    iconTint = Color(0xFFC084FC),
                    modifier = Modifier.weight(1f)
                )

                // Card 3: Chat Guia (Placeholder / 0)
                MetricSummaryCard(
                    title = "CHAT GUIA",
                    value = "0",
                    icon = Icons.Default.ChatBubbleOutline,
                    gradientColors = listOf(Color(0xFF064E3B), Color(0xFF059669)),
                    iconTint = Color(0xFF34D399),
                    modifier = Modifier.weight(1f)
                )
            }

            // Creative Touch: Emotional Insight Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = GreyishDarkBlue),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2A3D52))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF243342)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = Color(0xFFA855F7),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column {
                        Text(
                            text = if (totalCount > 0) "Insight de Progresso" else "Acompanhamento Emocional",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                        Text(
                            text = if (totalCount > 0 && topEmotion != null) {
                                "Sua emoção mais recorrente no período foi \"${topEmotion.title}\". O registro constante estimula a autorregulação!"
                            } else {
                                "Nenhum registro no período selecionado. Cada registro te ajuda a entender melhor seus padrões."
                            },
                            fontSize = 12.sp,
                            color = LightGrey,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            // "Filtrar por Período" Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = GreyishDarkBlue)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Header
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterAlt,
                            contentDescription = null,
                            tint = PrimaryBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Filtrar por Período",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                    }

                    // Period buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PeriodFilterButton(
                            title = "Hoje",
                            icon = Icons.Default.Schedule,
                            isSelected = currentFilter == DateFilter.HOJE,
                            onClick = { viewModel.setFilter(DateFilter.HOJE) },
                            modifier = Modifier.weight(1f)
                        )
                        PeriodFilterButton(
                            title = "7 Dias",
                            icon = Icons.Default.CalendarMonth,
                            isSelected = currentFilter == DateFilter.SETE_DIAS,
                            onClick = { viewModel.setFilter(DateFilter.SETE_DIAS) },
                            modifier = Modifier.weight(1f)
                        )
                        PeriodFilterButton(
                            title = "30 Dias",
                            icon = Icons.Default.CalendarMonth,
                            isSelected = currentFilter == DateFilter.TRINTA_DIAS,
                            onClick = { viewModel.setFilter(DateFilter.TRINTA_DIAS) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Date range inputs De / Até
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        DateFieldItem(
                            label = "DE:",
                            placeholder = "dd/mm/aaaa",
                            modifier = Modifier.weight(1f)
                        )
                        DateFieldItem(
                            label = "ATÉ:",
                            placeholder = "dd/mm/aaaa",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // "Emoções Frequentes" Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = GreyishDarkBlue)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Header
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.BarChart,
                            contentDescription = null,
                            tint = Color(0xFFA855F7),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Emoções Frequentes",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = White
                        )
                    }

                    if (filteredEntries.isEmpty()) {
                        // Empty State Container
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFF334155),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF243342)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Inbox,
                                        contentDescription = null,
                                        tint = LightGrey,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }

                                Text(
                                    text = "Nenhuma emoção registrada",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = White,
                                    textAlign = TextAlign.Center
                                )

                                Text(
                                    text = "Comece registrando suas emoções na aba Emoções",
                                    fontSize = 13.sp,
                                    color = LightGrey,
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                // Button with Purple-to-Blue gradient
                                Button(
                                    onClick = onNavigateToEmocoes,
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent
                                    ),
                                    modifier = Modifier
                                        .background(
                                            brush = Brush.horizontalGradient(
                                                colors = listOf(Color(0xFF9333EA), Color(0xFF3B82F6))
                                            ),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .height(44.dp)
                                ) {
                                    Text(
                                        text = "Registrar Primeira Emoção",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = White
                                    )
                                }
                            }
                        }
                    } else {
                        // Emotion Frequency Breakdown List
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            EmotionType.entries.forEach { emotion ->
                                val count = frequencies[emotion] ?: 0
                                if (count > 0) {
                                    val percentage = count.toFloat() / totalCount.toFloat()

                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                Icon(
                                                    imageVector = emotion.icon,
                                                    contentDescription = emotion.title,
                                                    tint = emotion.color,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                                Text(
                                                    text = emotion.title,
                                                    fontSize = 14.sp,
                                                    color = White,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            }
                                            Text(
                                                text = "$count (${(percentage * 100).toInt()}%)",
                                                fontSize = 13.sp,
                                                color = LightGrey
                                            )
                                        }

                                        LinearProgressIndicator(
                                            progress = { percentage },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(8.dp)
                                                .clip(RoundedCornerShape(4.dp)),
                                            color = emotion.color,
                                            trackColor = Color(0xFF243342)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricSummaryCard(
    title: String,
    value: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GreyishDarkBlue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Icon with glowing rounded container
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(colors = gradientColors)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = White,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Big Metric Value
            Text(
                text = value,
                fontSize = if (value.length > 6) 16.sp else 22.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                textAlign = TextAlign.Center
            )

            // Metric Title
            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = LightGrey,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun PeriodFilterButton(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) PrimaryBlue else Color(0xFF243342))
            .border(
                width = 1.dp,
                color = if (isSelected) PrimaryBlue else Color(0xFF33475B),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) White else LightGrey,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) White else LightGrey
            )
        }
    }
}

@Composable
private fun DateFieldItem(
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = LightGrey,
            fontWeight = FontWeight.SemiBold
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF243342))
                .border(1.dp, Color(0xFF33475B), RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = placeholder,
                    color = Color(0xFF64748B),
                    fontSize = 13.sp
                )
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = LightGrey,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

