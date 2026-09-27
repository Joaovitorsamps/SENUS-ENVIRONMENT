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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaax_sensus.data.DateFilter
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
fun DadosScreen(
    viewModel: EmotionViewModel,
    onNavigateToEmocoes: () -> Unit,
    onEditProfile: () -> Unit = {},
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
            .background(SensusSageTeal)
    ) {
        SENSUSHeader(
            userName = userName,
            onLogoutClick = onLogoutClick
        )

        // Botão Editar Perfil - Pill arredondada
        Button(
            onClick = onEditProfile,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .height(44.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SensusDarkTaupe),
            shape = RoundedCornerShape(22.dp)
        ) {
            Text("Editar Perfil & Preferências", color = SensusWarmCream, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top 2 Metric Cards - Estilo Warm Cream com destaque em Terracotta (Image 3)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Card 1: Total Registros
                MetricSummaryCard(
                    title = "REGISTROS",
                    value = totalCount.toString(),
                    icon = Icons.Default.Adjust,
                    badgeColor = SensusTerracotta,
                    iconTint = SensusWarmCream,
                    modifier = Modifier.weight(1f)
                )

                // Card 2: Top Emoção
                MetricSummaryCard(
                    title = "TOP EMOÇÃO",
                    value = topEmotion?.title ?: "-",
                    icon = Icons.AutoMirrored.Filled.TrendingUp,
                    badgeColor = SensusMintGreen,
                    iconTint = SensusDarkTaupe,
                    modifier = Modifier.weight(1f)
                )
            }

            // Insight Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SensusWarmCream),
                border = androidx.compose.foundation.BorderStroke(1.dp, SensusCreamDark)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(SensusMintGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = SensusDarkTaupe,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Column {
                        Text(
                            text = if (totalCount > 0) "Insight de Progresso" else "Acompanhamento Emocional",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = SensusDarkTaupe
                        )
                        Text(
                            text = if (totalCount > 0 && topEmotion != null) {
                                "Sua emoção mais recorrente no período foi \"${topEmotion.title}\". Identificar padrões é o primeiro passo para o equilíbrio!"
                            } else {
                                "Nenhum registro no período selecionado. Cada marcação fortalece sua autorregulação."
                            },
                            fontSize = 12.sp,
                            color = SensusTaupeMuted,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            // "Filtrar por Período" Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SensusWarmCream),
                border = androidx.compose.foundation.BorderStroke(1.dp, SensusCreamDark)
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
                            tint = SensusTerracotta,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Filtrar por Período",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SensusDarkTaupe
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
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SensusWarmCream),
                border = androidx.compose.foundation.BorderStroke(1.dp, SensusCreamDark)
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
                            tint = SensusTerracotta,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Frequência de Emoções",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SensusDarkTaupe
                        )
                    }

                    if (filteredEntries.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = SensusCreamDark,
                                    shape = RoundedCornerShape(16.dp)
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
                                        .background(SensusCreamLight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Inbox,
                                        contentDescription = null,
                                        tint = SensusTaupeMuted,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }

                                Text(
                                    text = "Nenhuma emoção registrada",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SensusDarkTaupe,
                                    textAlign = TextAlign.Center
                                )

                                Text(
                                    text = "Comece registrando o que sente na aba Emoções",
                                    fontSize = 12.sp,
                                    color = SensusTaupeMuted,
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Button(
                                    onClick = onNavigateToEmocoes,
                                    shape = RoundedCornerShape(22.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = SensusTerracotta,
                                        contentColor = SensusWarmCream
                                    ),
                                    modifier = Modifier.height(46.dp)
                                ) {
                                    Text(
                                        text = "Registrar Primeira Emoção",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    } else {
                        // Breakdown
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
                                                    color = SensusDarkTaupe,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            }
                                            Text(
                                                text = "$count (${(percentage * 100).toInt()}%)",
                                                fontSize = 13.sp,
                                                color = SensusTaupeMuted,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }

                                        LinearProgressIndicator(
                                            progress = { percentage },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(8.dp)
                                                .clip(RoundedCornerShape(4.dp)),
                                            color = emotion.color,
                                            trackColor = SensusCreamLight
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun MetricSummaryCard(
    title: String,
    value: String,
    icon: ImageVector,
    badgeColor: Color,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SensusWarmCream),
        border = androidx.compose.foundation.BorderStroke(1.dp, SensusCreamDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(badgeColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = value,
                fontSize = if (value.length > 6) 16.sp else 24.sp,
                fontWeight = FontWeight.Bold,
                color = SensusDarkTaupe,
                textAlign = TextAlign.Center
            )

            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = SensusTaupeMuted,
                textAlign = TextAlign.Center,
                letterSpacing = 0.5.sp
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
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) SensusTerracotta else SensusCreamLight)
            .border(
                width = 1.dp,
                color = if (isSelected) SensusTerracotta else SensusCreamDark,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) SensusWarmCream else SensusDarkTaupe,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) SensusWarmCream else SensusDarkTaupe
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
            color = SensusDarkTaupe,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(SensusCreamLight)
                .border(1.dp, SensusCreamDark, RoundedCornerShape(14.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = placeholder,
                    color = SensusTaupeMuted,
                    fontSize = 13.sp
                )
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = SensusDarkTaupe,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
