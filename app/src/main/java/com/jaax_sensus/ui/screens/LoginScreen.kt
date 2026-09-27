package com.jaax_sensus.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
fun LoginScreen(
    onLogin: (username: String, password: String) -> Unit,
    onRegisterClick: () -> Unit = {},
    isLoading: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var localErrorMessage by remember { mutableStateOf<String?>(null) }
    val focusManager = LocalFocusManager.current

    val submitLogin = {
        if (username.isBlank()) {
            localErrorMessage = "Digite seu usuário ou e-mail."
        } else if (password.isBlank()) {
            localErrorMessage = "Digite sua senha."
        } else {
            localErrorMessage = null
            onLogin(username.trim(), password)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SensusSageTeal),
        contentAlignment = Alignment.Center
    ) {
        // Imagem botânica orgânica no fundo conforme design
        Image(
            painter = painterResource(id = R.drawable.bg_sensus_nature),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.45f
        )

        // Overlay suave para leitura perfeita
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SensusSageTeal.copy(alpha = 0.5f))
        )

        // Card central estilizado conforme mockup do designer (Image 2)
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
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Pill superior com logo - idêntico ao wireframe
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(SensusMintGreen)
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(SensusCreamLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_techtea),
                            contentDescription = "Logo SENSUS",
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
                        fontSize = 16.sp,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Bem-vindo!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = SensusDarkTaupe,
                    fontSize = 24.sp
                )

                Text(
                    text = "Seu espaço de autorregulação emocional",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SensusTaupeMuted,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Input Usuário / E-mail - Pill arredondada
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "USUÁRIO OU E-MAIL",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SensusDarkTaupe,
                        letterSpacing = 0.5.sp
                    )

                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            username = it
                            localErrorMessage = null
                        },
                        placeholder = {
                            Text(
                                text = "Digite seu usuário ou e-mail",
                                color = SensusTaupeMuted,
                                fontSize = 14.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = SensusDarkTaupe,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
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
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Input Senha - Pill arredondada
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "SENHA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SensusDarkTaupe,
                        letterSpacing = 0.5.sp
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            localErrorMessage = null
                        },
                        placeholder = {
                            Text(
                                text = "Digite sua senha",
                                color = SensusTaupeMuted,
                                fontSize = 14.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = SensusDarkTaupe,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { isPasswordVisible = !isPasswordVisible }
                            ) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (isPasswordVisible) "Ocultar senha" else "Exibir senha",
                                    tint = SensusDarkTaupe,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                submitLogin()
                            }
                        ),
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
                }

                val visibleErrorMessage = errorMessage ?: localErrorMessage
                if (visibleErrorMessage != null) {
                    Text(
                        text = visibleErrorMessage,
                        color = Color(0xFFC0392B),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botão Primário: Terracotta Orange Pill Button
                Button(
                    onClick = submitLogin,
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = SensusWarmCream,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Entrando...", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        } else {
                            Text(
                                text = "Entrar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = SensusWarmCream
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = SensusWarmCream,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Botão Secundário: Dark Taupe Pill Button
                Button(
                    onClick = onRegisterClick,
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SensusDarkTaupe,
                        contentColor = SensusWarmCream
                    )
                ) {
                    Text(
                        text = "Criar nova conta",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SensusWarmCream
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Quick Demo Login Button
                TextButton(
                    onClick = {
                        username = "demo_user"
                        password = "demo_password"
                        onLogin("demo_user", password)
                    },
                    enabled = !isLoading
                ) {
                    Text(
                        text = "Entrar como visitante (Demo)",
                        color = SensusDarkTaupe,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
