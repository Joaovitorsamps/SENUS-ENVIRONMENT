package com.jaax_sensus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaax_sensus.ui.theme.DeepNavyBlue
import com.jaax_sensus.ui.theme.GreyishDarkBlue
import com.jaax_sensus.ui.theme.White

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
        focusedContainerColor = DeepNavyBlue,
        unfocusedContainerColor = DeepNavyBlue,
        focusedBorderColor = Color(0xFF3B82F6),
        unfocusedBorderColor = Color(0xFF334155),
        focusedTextColor = White,
        unfocusedTextColor = White,
        focusedLabelColor = Color(0xFF93C5FD),
        unfocusedLabelColor = Color(0xFF94A3B8)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DeepNavyBlue)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack, enabled = !isLoading) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = White)
            }
            Text("Editar perfil", color = White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Text(
            text = "Atualize seus dados pessoais",
            color = Color(0xFF94A3B8),
            fontSize = 14.sp
        )

        ProfileField("Nome de usuário", name, { name = it }, colors, isLoading)
        ProfileField("País", country, { country = it }, colors, isLoading)
        ProfileField("Estado", state, { state = it }, colors, isLoading)
        ProfileField("Cidade", city, { city = it }, colors, isLoading)

        if (errorMessage != null) {
            Text(errorMessage, color = Color(0xFFEF4444), fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = { onSave(name, country, state, city) },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB), contentColor = White)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = White, strokeWidth = 2.dp)
            } else {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text("Salvar alterações", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
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
        label = { Text(label) },
        singleLine = true,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = colors
    )
}
