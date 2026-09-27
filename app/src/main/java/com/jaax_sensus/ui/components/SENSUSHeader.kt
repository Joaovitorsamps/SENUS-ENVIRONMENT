package com.jaax_sensus.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaax_sensus.R
import com.jaax_sensus.ui.theme.SensusCreamLight
import com.jaax_sensus.ui.theme.SensusDarkTaupe
import com.jaax_sensus.ui.theme.SensusMintGreen
import com.jaax_sensus.ui.theme.SensusTerracotta
import com.jaax_sensus.ui.theme.SensusWarmCream

@Composable
fun SENSUSHeader(
    userName: String = "jose",
    onLogoutClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(SensusDarkTaupe)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo & Brand Pill - Inspirado na barra superior do conceito
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(SensusMintGreen)
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(SensusWarmCream),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_techtea),
                    contentDescription = "SENSUS Logo",
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Fit
                )
            }
            Text(
                text = "SENSUS",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = SensusDarkTaupe,
                fontSize = 15.sp,
                letterSpacing = 0.5.sp
            )
        }

        // Right side: Boas-vindas e botão de ação em Terracotta
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Pill com status de bem-estar / eco
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(SensusWarmCream.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = "Equilíbrio emocional",
                    tint = SensusMintGreen,
                    modifier = Modifier.size(18.dp)
                )
            }

            // User greeting
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Olá,",
                    fontSize = 11.sp,
                    color = SensusWarmCream.copy(alpha = 0.8f)
                )
                Text(
                    text = userName,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = SensusWarmCream
                )
            }

            // Logout button com acento Terracotta do design
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SensusTerracotta),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onLogoutClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "Sair",
                        tint = SensusCreamLight,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
