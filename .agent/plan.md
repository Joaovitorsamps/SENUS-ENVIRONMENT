# Project Plan

TechTEA - Diário Emocional: An emotional diary app for neurodivergent users. 
Features:
- Dark Mode by default.
- General Background: Deep navy blue (#131E29).
- Card Backgrounds: Greyish dark blue (#1C2A38) with rounded corners.
- Typography: Clean sans-serif, white titles, light grey subtitles.
- Screens:
  1. Home / New Emotion ("Emoções"): Header with logo, user greeting, emotion selector (2x3 grid: Happy, Sad, Angry, Anxious, Tired, Calm), text field for context, "Save to Diary" button, and recent history.
  2. Metrics and Analysis ("Dados"): Summary cards (Total entries, Top emotion, Chat Guide count), time period filter (Today, 7 days, 30 days, manual), and frequent emotions statistics.
  3. Bottom Navigation Bar: Emoções, Chat Guia (inactive/placeholder), Diário, Dados.
  
Interactions:
- Select emotion -> Enter text -> Save.
- Data view shows stats based on time filters.

Images provided:
- input_file_0.png: Shows the 'Dados' (Metrics) screen with 'Filtrar por Período' and 'Emoções Frequentes'.
- input_file_1.png: Shows the 'Emoções' (Home) screen with a 2x3 grid of colorful emotion buttons.
- input_file_2.png: Shows the 'Dados' screen with top summary cards (Registros, Top Emoção, Chat Guia).
- input_file_3.png: Shows the 'Diário' screen with a 'Novo Registro' card containing icons for emotions and a text area.

## Project Brief

# TechTEA - Diário Emocional: Project Brief

## Features
- **Simplified Emotion Logging**: A user-friendly 2x3 grid interface (Happy, Sad, Angry, Anxious, Tired, Calm) designed for quick emotional check-ins, paired with a contextual text field.
- **Metrics & Analysis Dashboard**: A data-driven screen providing summary cards for total entries and top emotions to help users identify patterns.
- **Time-Period Filtering**: Robust filtering capabilities allowing users to view emotional statistics for specific intervals (Today, 7 days, 30 days, or manual ranges).
- **Recent History Feed**: A chronological list of emotional entries on the home screen for immediate self-reflection.

## High-Level Technical Stack
- **Kotlin**: The primary language for modern, safe, and concise Android development.
- **Jetpack Compose**: The core UI toolkit for building a declarative, Material 3-compliant interface.
- **Jetpack Navigation 3**: A state-driven navigation architecture to manage the flow between "Emoções", "Diário", and "Dados" screens.
- **Compose Material Adaptive**: Essential library for ensuring the UI components and layouts are responsive across different screen sizes and orientations.
- **Kotlin Coroutines**: For handling asynchronous operations and ensuring a smooth, non-blocking user experience.

## UI Design Image
![UI Design](input_file_1.png)
Image path = input_file_1.png

## Implementation Steps

### Task_1_Setup_Theme_Nav: Configure the project theme (Dark Mode #131E29) and implement the Navigation 3 structure with a Bottom Navigation Bar for 'Emoções', 'Chat Guia', 'Diário', and 'Dados'.
- **Status:** COMPLETED
- **Acceptance Criteria:**
  - Project builds successfully with Navigation 3 and Material 3 dependencies.
  - Dark theme matches colors from input_file_1.png.
  - Bottom navigation bar is functional and switches between screen placeholders.
- **StartTime:** 2026-09-07 17:58:26 GMT-04:00

### Task_2_Emotion_Entry_Screen: Implement the 'Emoções' home screen featuring the 2x3 emotion grid, text entry for context, and the recent history feed.
- **Status:** COMPLETED
- **Acceptance Criteria:**
  - The implemented UI must match the design provided in input_file_1.png.
  - Emotion selection grid (2x3) is interactive.
  - Text field and 'Salvar no Diário' button are functional.

### Task_3_Diary_and_Metrics_Screens: Implement the 'Diário' screen to display entry history and the 'Dados' screen with summary cards, period filters, and emotion statistics.
- **Status:** COMPLETED
- **Acceptance Criteria:**
  - The implemented UI must match the design provided in input_file_3.png.
  - The implemented UI must match the design provided in input_file_0.png and input_file_2.png.
  - Metrics summary cards display correct totals for the current session.

### Task_4_Run_and_Verify: Conduct a final run of the application to ensure stability, verify UI fidelity across all screens, and confirm requirements alignment.
- **Status:** COMPLETED
- **Acceptance Criteria:**
  - build pass
  - app does not crash
  - make sure all existing tests pass
  - verified application stability
  - UI aligns with all provided design images (input_file_0.png, input_file_1.png, input_file_2.png, input_file_3.png).

