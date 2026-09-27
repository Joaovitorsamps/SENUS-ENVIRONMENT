package com.jaax_sensus.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaax_sensus.R
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
fun RegisterScreen(
    isLoading: Boolean,
    errorMessage: String?,
    onRegister: (username: String, email: String, password: String) -> Boolean,
    onBackToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmation by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmationVisible by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf<String?>(null) }

    val submit = {
        if (username.isBlank() || email.isBlank() || password.isBlank() || confirmation.isBlank()) {
            localError = "Preencha todos os campos."
        } else if (!email.contains("@")) {
            localError = "E-mail inválido."
        } else if (password.length < 6) {
            localError = "A senha deve conter ao menos 6 dígitos."
        } else if (password != confirmation) {
            localError = "As senhas não coincidem."
        } else {
            localError = null
            onRegister(username.trim(), email.trim(), password)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SensusSageTeal),
        contentAlignment = Alignment.Center
    ) {
        // Imagem botânica orgânica no fundo
        Image(
            painter = painterResource(id = R.drawable.bg_sensus_nature),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.45f
        )

        // Overlay suave
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SensusSageTeal.copy(alpha = 0.5f))
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = SensusWarmCream),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, SensusCreamDark),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 26.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Row: Voltar + Pill
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = onBackToLogin,
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
                            .clip(RoundedCornerShape(20.dp))
                            .background(SensusMintGreen)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "NOVA CONTA",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = SensusDarkTaupe
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Criar Perfil SENSUS",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = SensusDarkTaupe,
                    fontSize = 22.sp
                )

                Text(
                    text = "Acompanhe sua regulação emocional",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SensusTaupeMuted,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Campo Nome
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it; localError = null },
                    label = { Text("Nome Completo", color = SensusDarkTaupe, fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null, tint = SensusDarkTaupe, modifier = Modifier.size(20.dp))
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SensusCreamLight,
                        unfocusedContainerColor = SensusCreamLight,
                        focusedBorderColor = SensusTerracotta,
                        unfocusedBorderColor = SensusCreamDark,
                        focusedTextColor = SensusTaupeDark,
                        unfocusedTextColor = SensusTaupeDark
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Campo Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it; localError = null },
                    label = { Text("E-mail", color = SensusDarkTaupe, fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null, tint = SensusDarkTaupe, modifier = Modifier.size(20.dp))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SensusCreamLight,
                        unfocusedContainerColor = SensusCreamLight,
                        focusedBorderColor = SensusTerracotta,
                        unfocusedBorderColor = SensusCreamDark,
                        focusedTextColor = SensusTaupeDark,
                        unfocusedTextColor = SensusTaupeDark
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Campo Senha
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it; localError = null },
                    label = { Text("Senha", color = SensusDarkTaupe, fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = SensusDarkTaupe, modifier = Modifier.size(20.dp))
                    },
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = SensusDarkTaupe,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SensusCreamLight,
                        unfocusedContainerColor = SensusCreamLight,
                        focusedBorderColor = SensusTerracotta,
                        unfocusedBorderColor = SensusCreamDark,
                        focusedTextColor = SensusTaupeDark,
                        unfocusedTextColor = SensusTaupeDark
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Campo Confirmação
                OutlinedTextField(
                    value = confirmation,
                    onValueChange = { confirmation = it; localError = null },
                    label = { Text("Confirmar Senha", color = SensusDarkTaupe, fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = SensusDarkTaupe, modifier = Modifier.size(20.dp))
                    },
                    trailingIcon = {
                        IconButton(onClick = { isConfirmationVisible = !isConfirmationVisible }) {
                            Icon(
                                imageVector = if (isConfirmationVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = SensusDarkTaupe,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    visualTransformation = if (isConfirmationVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SensusCreamLight,
                        unfocusedContainerColor = SensusCreamLight,
                        focusedBorderColor = SensusTerracotta,
                        unfocusedBorderColor = SensusCreamDark,
                        focusedTextColor = SensusTaupeDark,
                        unfocusedTextColor = SensusTaupeDark
                    )
                )

                val message = errorMessage ?: localError
                if (message != null) {
                    Text(
                        text = message,
                        color = Color(0xFFC0392B),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Botão Cadastrar - Terracotta
                Button(
                    onClick = { submit() },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .shadow(4.dp, RoundedCornerShape(26.dp)),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SensusTerracotta,
                        contentColor = SensusWarmCream
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = SensusWarmCream,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Criando perfil...", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    } else {
                        Text("Concluir Cadastro", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SensusWarmCream)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onBackToLogin) {
                    Text("Já tenho cadastro · Entrar", color = SensusDarkTaupe, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}