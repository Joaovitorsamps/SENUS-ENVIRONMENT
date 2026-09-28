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
import androidx.compose.ui.text.TextStyle
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
            localErrorMessage = "Digite seu e-mail."
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
            .background(SensusSageTeal)
            .imePadding()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState()),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SensusWarmCream),
            border = androidx.compose.foundation.BorderStroke(1.dp, SensusCreamDark),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Circular White Logo Container
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(SensusCreamLight)
                        .shadow(4.dp, CircleShape)
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_techtea),
                        contentDescription = "Logo SENUS",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Bem-vindo!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = SensusDarkTaupe,
                    fontSize = 24.sp
                )

                Text(
                    text = "SENSUS",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SensusTaupeMuted,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Input Usuário
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "USUÁRIO OU E-MAIL",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
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
                                text = "Digite seu nome ou e-mail",
                                color = SensusTaupeMuted,
                                fontSize = 14.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = SensusTaupeMuted,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        textStyle = TextStyle(color = SensusTaupeDark, fontSize = 14.sp),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
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

                // Input Senha
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "SENHA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
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
                                tint = SensusTaupeMuted,
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
                                    tint = SensusTaupeMuted,
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
                        textStyle = TextStyle(color = SensusTaupeDark, fontSize = 14.sp),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
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
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botão Entrar
                Button(
                    onClick = submitLogin,
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .shadow(8.dp, RoundedCornerShape(12.dp), ambientColor = SensusTerracotta),
                    shape = RoundedCornerShape(12.dp),
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
                            Text("Entrando...", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        } else {
                            Text(
                                text = "Entrar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                TextButton(
                    onClick = onRegisterClick,
                    enabled = !isLoading
                ) {
                    Text(
                        text = "Ainda não tenho uma conta",
                        color = SensusTerracotta,
                        fontSize = 13.sp
                    )
                }

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
                        text = "Entrar como (Demo)",
                        color = SensusTerracotta,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

