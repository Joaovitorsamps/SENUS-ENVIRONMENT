package com.jaax_sensus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@Composable
fun EmocoesScreen(
    userName: String = "jose",
    onEmotionSelected: (EmotionType) -> Unit,
    onLogoutClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SensusSageTeal)
    ) {
        SENSUSHeader(
            userName = userName,
            onLogoutClick = onLogoutClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Banner de Regulação Consciente - Estilo Card Cream do design
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SensusWarmCream),
                border = androidx.compose.foundation.BorderStroke(1.dp, SensusCreamDark),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(SensusMintGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Spa,
                            contentDescription = null,
                            tint = SensusDarkTaupe,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Pausa Consciente",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = SensusDarkTaupe
                        )
                        Text(
                            text = "Respire fundo. Todas as emoções são válidas e acolhidas aqui.",
                            fontSize = 12.sp,
                            color = SensusTaupeMuted,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            // Pill de Pergunta / Instrução
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SensusWarmCream.copy(alpha = 0.35f))
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Como você está se sentindo agora?",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = SensusDarkTaupe,
                    fontSize = 15.sp
                )
            }

            // Grade de Emoções 2x3 com cards acolhedores
            val emotions = EmotionType.entries
            for (i in emotions.indices step 2) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    val firstEmotion = emotions[i]
                    EmotionCard(
                        emotion = firstEmotion,
                        onClick = { onEmotionSelected(firstEmotion) },
                        modifier = Modifier.weight(1f)
                    )

                    if (i + 1 < emotions.size) {
                        val secondEmotion = emotions[i + 1]
                        EmotionCard(
                            emotion = secondEmotion,
                            onClick = { onEmotionSelected(secondEmotion) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun EmotionCard(
    emotion: EmotionType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(22.dp))
            .shadow(4.dp, RoundedCornerShape(22.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = SensusWarmCream),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, SensusCreamDark)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Círculo colorido da emoção
                Box(
                    modifier = Modifier
                        .size(62.dp)
                        .clip(CircleShape)
                        .background(emotion.color),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = emotion.icon,
                        contentDescription = emotion.title,
                        tint = emotion.textColor,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = emotion.title,
                    color = SensusDarkTaupe,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
