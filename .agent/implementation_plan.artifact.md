# Implementation Plan - Theme and Navigation 3 for TechTEA

Configure the project theme and implement Navigation 3 with a bottom navigation bar for the TechTEA app.

## User Review Required

> [!IMPORTANT]
> The app will be strictly in **Dark Mode** as requested. The theme uses specific navy blue and greyish blue colors derived from the requirements.

> [!NOTE]
> Navigation 3 is used for state-driven navigation, integrating with the Material 3 Adaptive library for future-proofing.

## Proposed Changes

### [Theme]

#### [MODIFY] [Color.kt](file:///C:/Users/Juliano/AndroidStudioProjects/JAAXSENSUS/app/src/main/java/com/jaax_sensus/ui/theme/Color.kt)
- Define `DeepNavyBlue` (#131E29) and `GreyishDarkBlue` (#1C2A38).
- Define text colors: `White` and `LightGrey`.

#### [MODIFY] [Theme.kt](file:///C:/Users/Juliano/AndroidStudioProjects/JAAXSENSUS/app/src/main/java/com/jaax_sensus/ui/theme/Theme.kt)
- Create a `DarkColorScheme` using the defined colors.
- Force dark mode in `JAAXSENSUSTheme`.
- Set background and surface colors to the navy blue variants.

#### [MODIFY] [Type.kt](file:///C:/Users/Juliano/AndroidStudioProjects/JAAXSENSUS/app/src/main/java/com/jaax_sensus/ui/theme/Type.kt)
- Configure typography with sans-serif font family.
- Set default text colors for titles and body.

### [Navigation]

#### [NEW] [NavKeys.kt](file:///C:/Users/Juliano/AndroidStudioProjects/JAAXSENSUS/app/src/main/java/com/jaax_sensus/navigation/NavKeys.kt)
- Define serializable routes for:
  - `Emocoes` (Home)
  - `ChatGuia` (Placeholder)
  - `Diario`
  - `Dados`

#### [NEW] [Screens.kt](file:///C:/Users/Juliano/AndroidStudioProjects/JAAXSENSUS/app/src/main/java/com/jaax_sensus/ui/screens/Screens.kt)
- Create basic composable screens for each route.
- `ChatGuia` will display "Coming Soon".

#### [MODIFY] [MainActivity.kt](file:///C:/Users/Juliano/AndroidStudioProjects/JAAXSENSUS/app/src/main/java/com/jaax_sensus/MainActivity.kt)
- Implement `NavBackStack` and `NavDisplay`.
- Add a `Scaffold` with a `NavigationBar` for bottom navigation.
- Wire up the 4 items with correct icons (Grid, ChatBubble, Book, BarChart).

## Verification Plan

### Automated Tests
- Run `./gradlew :app:assembleDebug` to ensure compilation.

### Manual Verification
- Launch the app and verify:
  - Bottom navigation switches between screens.
  - "Emoções" is the start destination.
  - The background is Deep Navy Blue (#131E29).
  - Cards (where used) are Greyish Dark Blue (#1C2A38).
  - Text colors match the spec.
