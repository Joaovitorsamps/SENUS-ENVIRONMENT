<div align="center">

# 🧠 JAAXSENSUS — Mobile

**Aplicativo Android de registro emocional para pessoas com TEA (Transtorno do Espectro Autista)**

![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-2024.09-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Android](https://img.shields.io/badge/Android-API_24+-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

</div>

---

## 📖 Sobre o Projeto

O **JAAXSENSUS** é o módulo mobile do ecossistema **SENSUS** — uma plataforma voltada ao suporte emocional e ao autoconhecimento de pessoas com TEA. O app permite que o usuário registre como se sente ao longo do dia, acompanhe seu histórico emocional e acesse um guia de apoio conversacional.

> Este repositório faz parte do projeto maior [SENUS-ENVIRONMENT](https://github.com/Joaovitorsamps/SENUS-ENVIRONMENT), abrigado na branch `MOBILE`.

---

## ✨ Funcionalidades

| Tela | Descrição |
|------|-----------|
| 🔐 **Login** | Autenticação simples com nome de usuário |
| 😊 **Emoções** | Seleção visual de 6 estados emocionais (Feliz, Triste, Irritado, Ansioso, Cansado, Calmo) |
| 📓 **Diário** | Registro de anotações vinculadas à emoção selecionada, com data/hora automática |
| 📊 **Dados** | Visualização do histórico emocional com filtros por período (Hoje, 7 dias, 30 dias, Todos) |
| 💬 **Chat Guia** | Tela de orientação e suporte conversacional |

---

## 🏗️ Arquitetura

O projeto segue a arquitetura **MVVM** com separação clara de responsabilidades:

```
app/src/main/java/com/jaax_sensus/
├── data/
│   └── EmotionModels.kt       # Models: EmotionType, DiaryEntry, DateFilter
├── navigation/
│   └── NavKeys.kt             # Rotas de navegação (AppRoute)
├── ui/
│   ├── components/
│   │   └── SENSUSHeader.kt    # Componente de cabeçalho reutilizável
│   ├── screens/
│   │   ├── LoginScreen.kt
│   │   ├── EmocoesScreen.kt
│   │   ├── DiarioScreen.kt
│   │   ├── DadosScreen.kt
│   │   └── ChatGuiaScreen.kt
│   ├── theme/
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   └── viewmodel/
│       └── EmotionViewModel.kt  # Estado global da aplicação via StateFlow
└── MainActivity.kt              # Entry point + NavDisplay (Navigation3)
```

---

## 🛠️ Tecnologias & Dependências

- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** — UI declarativa
- **[Navigation3](https://developer.android.com/guide/navigation/navigation3)** — Navegação type-safe com back stack
- **[ViewModel + StateFlow](https://developer.android.com/topic/libraries/architecture/viewmodel)** — Gerenciamento de estado reativo
- **[Room](https://developer.android.com/training/data-storage/room)** — Persistência local (estrutura preparada)
- **[Retrofit + Moshi](https://square.github.io/retrofit/)** — Comunicação com API REST
- **[Coil](https://coil-kt.github.io/coil/)** — Carregamento de imagens
- **[CameraX](https://developer.android.com/training/camerax)** — Suporte a câmera
- **[DataStore](https://developer.android.com/topic/libraries/architecture/datastore)** — Preferências do usuário
- **[Material3 Adaptive](https://developer.android.com/develop/ui/compose/layouts/adaptive)** — Layout adaptativo

---

## 🚀 Como Executar

### Pré-requisitos

- Android Studio **Ladybug** ou superior
- JDK 17+
- Android SDK API 24+

### Passos

```bash
# 1. Clone o repositório e acesse a branch MOBILE
git clone https://github.com/Joaovitorsamps/SENUS-ENVIRONMENT.git
cd SENUS-ENVIRONMENT
git checkout MOBILE

# 2. Abra no Android Studio
# File > Open > selecione a pasta clonada

# 3. (Opcional) Configure o Supabase em local.properties:
# supabase.url=https://seu-projeto.supabase.co
# supabase.anon.key=sua-chave-anon-publica

# 4. Sincronize o Gradle e execute no emulador ou dispositivo físico
```

### ⚙️ Configuração do Backend (Supabase)

Para conectar o app ao banco de dados Supabase da equipe, adicione as seguintes linhas no seu arquivo `local.properties` na raiz do projeto:

```properties
supabase.url=https://seu-projeto.supabase.co
supabase.anon.key=sua-chave-anonima-publica
```

> 💡 **Nota:** Se as chaves não forem configuradas no `local.properties`, o app inicia automaticamente em **modo offline/demo**, preservando toda a navegação e operações em memória.


---

## 🤝 Contribuindo

Este projeto faz parte de um trabalho acadêmico/institucional. Contribuições são bem-vindas via Pull Request na branch `MOBILE`.

1. Faça um fork do repositório
2. Crie sua feature branch: `git checkout -b feature/minha-funcionalidade`
3. Commit suas mudanças: `git commit -m feat: adiciona minha funcionalidade`
4. Push para a branch: `git push origin feature/minha-funcionalidade`
5. Abra um Pull Request para `MOBILE`

---

## 📄 Licença

Distribuído sob a licença MIT.


