package com.jaax_sensus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun EditarPerfilScreen(
    initialName: String,
    isLoading: Boolean,
    errorMessage: String?,
    onSave: (String, String, String, String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(initialName) }
    var country by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    val colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = SensusCreamLight,
        unfocusedContainerColor = SensusCreamLight,
        focusedBorderColor = SensusTerracotta,
        unfocusedBorderColor = SensusCreamDark,
        focusedTextColor = SensusTaupeDark,
        unfocusedTextColor = SensusTaupeDark,
        focusedLabelColor = SensusTerracotta,
        unfocusedLabelColor = SensusDarkTaupe
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SensusSageTeal)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = SensusWarmCream),
            border = androidx.compose.foundation.BorderStroke(1.dp, SensusCreamDark),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Top Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = onBack,
                        enabled = !isLoading,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SensusCreamLight)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = SensusDarkTaupe,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(SensusMintGreen)
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PERFIL",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SensusDarkTaupe
                        )
                    }
                }

                Text(
                    text = "Editar Perfil",
                    color = SensusDarkTaupe,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Personalize suas informações no SENSUS",
                    color = SensusTaupeMuted,
                    fontSize = 13.sp
                )

                ProfileField("Nome de usuário", name, { name = it }, colors, isLoading)
                ProfileField("País", country, { country = it }, colors, isLoading)
                ProfileField("Estado", state, { state = it }, colors, isLoading)
                ProfileField("Cidade", city, { city = it }, colors, isLoading)

                if (errorMessage != null) {
                    Text(errorMessage, color = Color(0xFFC0392B), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = { onSave(name, country, state, city) },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SensusTerracotta,
                        contentColor = SensusWarmCream
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = SensusWarmCream, strokeWidth = 2.dp, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Salvando...", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    } else {
                        Icon(Icons.Default.Save, contentDescription = null, tint = SensusWarmCream)
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Text("Salvar Alterações", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SensusWarmCream)
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    colors: androidx.compose.material3.TextFieldColors,
    enabled: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 13.sp) },
        singleLine = true,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = colors
    )
}
