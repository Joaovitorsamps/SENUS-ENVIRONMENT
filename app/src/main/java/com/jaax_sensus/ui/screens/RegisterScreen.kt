package com.jaax_sensus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaax_sensus.ui.theme.White

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

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color(0xFF0B132B),
        unfocusedContainerColor = Color(0xFF0B132B),
        focusedBorderColor = Color(0xFF3B82F6),
        unfocusedBorderColor = Color(0xFF334155),
        focusedTextColor = White,
        unfocusedTextColor = White,
        focusedLeadingIconColor = Color(0xFF94A3B8),
        unfocusedLeadingIconColor = Color(0xFF94A3B8)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0B132B))
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Criar conta",
            style = MaterialTheme.typography.headlineSmall,
            color = White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Comece a acompanhar suas emoções no SENSUS",
            color = Color(0xFF94A3B8),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(28.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it; localError = null },
            label = { Text("Nome de usuário") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it; localError = null },
            label = { Text("E-mail") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            singleLine = true,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors
        )
        Spacer(modifier = Modifier.height(12.dp))
        PasswordField(
            value = password,
            label = "Senha",
            visible = isPasswordVisible,
            onValueChange = { password = it; localError = null },
            onToggleVisibility = { isPasswordVisible = !isPasswordVisible },
            colors = fieldColors
        )
        Spacer(modifier = Modifier.height(12.dp))
        PasswordField(
            value = confirmation,
            label = "Confirmar senha",
            visible = isConfirmationVisible,
            onValueChange = { confirmation = it; localError = null },
            onToggleVisibility = { isConfirmationVisible = !isConfirmationVisible },
            colors = fieldColors
        )

        val displayedError = localError ?: errorMessage
        if (displayedError != null) {
            Text(
                text = displayedError,
                color = Color(0xFFEF4444),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                localError = when {
                    username.isBlank() || email.isBlank() || password.isBlank() -> "Preencha todos os campos."
                    !email.contains("@") -> "Digite um e-mail válido."
                    password.length < 6 -> "A senha deve ter pelo menos 6 caracteres."
                    password != confirmation -> "As senhas não coincidem."
                    else -> null
                }
                if (localError == null) onRegister(username, email, password)
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB), contentColor = White)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = White, strokeWidth = 2.dp)
            } else {
                Text("Cadastrar", fontSize = 16.sp)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Já tenho uma conta", color = Color(0xFF94A3B8), fontSize = 13.sp)
            TextButton(onClick = onBackToLogin, enabled = !isLoading) {
                Text("Entrar", color = Color(0xFF60A5FA), fontSize = 13.sp)
            }
        }
    }
}

@Composable
private fun PasswordField(
    value: String,
    label: String,
    visible: Boolean,
    onValueChange: (String) -> Unit,
    onToggleVisibility: () -> Unit,
    colors: androidx.compose.material3.TextFieldColors
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
        trailingIcon = {
            IconButton(onClick = onToggleVisibility) {
                Icon(
                    imageVector = if (visible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (visible) "Ocultar senha" else "Exibir senha"
                )
            }
        },
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = colors
    )
}