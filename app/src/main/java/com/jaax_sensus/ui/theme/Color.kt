package com.jaax_sensus.ui.theme

import androidx.compose.ui.graphics.Color

// === Paleta Oficial SENSUS (Design Team) ===
val SensusDarkTaupe = Color(0xFF665C52)       // #665C52 - Texto principal, detalhes escuros, barra superior/inferior
val SensusSageTeal = Color(0xFF74B3A7)        // #74B3A7 - Fundo base relaxante / identidade visual
val SensusMintGreen = Color(0xFFA3CCAF)       // #A3CCAF - Acentos suaves, tags, elementos secundários
val SensusWarmCream = Color(0xFFE6E1CF)       // #E6E1CF - Cartões principais, inputs, painéis de conteúdo
val SensusTerracotta = Color(0xFFCC5B14)      // #CC5B14 - Botões de ação primária (CTA), seleções ativas

// Variações e tons auxiliares para profundidade e contraste
val SensusSageDark = Color(0xFF558F84)        // Verde sálvia mais escuro para cards secundários
val SensusSageLight = Color(0xFF8EC4BB)       // Verde sálvia claro para realces
val SensusCreamLight = Color(0xFFF7F5EE)      // Creme claro para campos de texto e áreas internas
val SensusCreamDark = Color(0xFFD6CDB7)       // Creme com tom de borda
val SensusTaupeDark = Color(0xFF473F37)       // Taupe profundo para textos de alto contraste
val SensusTaupeMuted = Color(0xFF8A7F73)      // Taupe suave para textos secundários / legendas
val SensusGreenAccent = Color(0xFF5A9977)     // Verde confirmação / sucesso

// Mapeamento semântico para componentes existentes
val DeepNavyBlue = SensusSageTeal             // Background principal do app agora é o Sage Teal
val GreyishDarkBlue = SensusDarkTaupe         // Barras / headers agora usam o Dark Taupe
val LightGrey = SensusTaupeMuted              // Textos secundários
val White = Color(0xFFFFFFFF)
val TextWhite = SensusWarmCream               // Textos em superfícies escuras
val TextLightGrey = SensusTaupeMuted

// Accent / UI colors
val PrimaryBlue = SensusTerracotta            // Acento primário do app agora é Terracotta Orange
val SurfaceVariantDark = SensusWarmCream      // Superfícies de cards agora usam o Warm Cream
val BorderDark = SensusCreamDark

// Emotion colors ajustadas à harmonia do design
val HappyYellow = Color(0xFFE5B543)
val SadBlue = Color(0xFF579DAE)
val AngryRed = Color(0xFFD4483B)
val AnxiousOrange = SensusTerracotta
val TiredPurple = Color(0xFF886FA6)
val CalmGreen = Color(0xFF65A685)