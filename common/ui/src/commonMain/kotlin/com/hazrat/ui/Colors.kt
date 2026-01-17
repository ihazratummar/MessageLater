package com.hazrat.ui

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


/**
 * @author hazratummar
 * Created on 10/01/26
 */

object AppColors {
    // Brand
    val BrandPrimary = Color(0xFF0F172A) // Slate 900
    val BrandSecondary = Color(0xFFF1F5F9) // Slate 100

    // Backgrounds - Light
    val BgPrimaryLight = Color(0xFFFFFFFF) // White
    val BgSecondaryLight = Color(0xFFF8FAFC) // Slate 50
    val BgTertiaryLight = Color(0xFFF1F5F9) // Slate 100

    // Backgrounds - Dark
    val BgPrimaryDark = Color(0xFF0F172A) // Slate 900
    val BgSecondaryDark = Color(0xFF1E293B) // Slate 800
    val BgTertiaryDark = Color(0xFF334155) // Slate 700

    // Text - Light
    val TextPrimaryLight = Color(0xFF0F172A) // Slate 900
    val TextSecondaryLight = Color(0xFF475569) // Slate 600
    val TextMutedLight = Color(0xFF94A3B8) // Slate 400

    // Text - Dark
    val TextPrimaryDark = Color(0xFFF8FAFC) // Slate 50
    val TextSecondaryDark = Color(0xFFCBD5E1) // Slate 300
    val TextMutedDark = Color(0xFF64748B) // Slate 500

    val TextInverse = Color(0xFFFFFFFF) // White

    // Status (Same for both themes)
    val StatusSuccess = Color(0xFF16A34A) // Green 600
    val StatusWarning = Color(0xFFD97706) // Amber 600
    val StatusError = Color(0xFFDC2626) // Red 600
    val StatusPro = Color(0xFFF59E0B) // Amber 500

    // Surface variants
    val SurfaceLight = Color(0xFFFFFFFF)
    val SurfaceDark = Color(0xFF1E293B)
    val OutlineLight = Color(0xFFE2E8F0) // Slate 200
    val OutlineDark = Color(0xFF475569) // Slate 600
}

// ==========================================================================
// MATERIAL 3 COLOR SCHEMES
// ==========================================================================

val LightColorScheme =
    lightColorScheme(
        primary = AppColors.BrandPrimary,
        onPrimary = AppColors.TextInverse,
        primaryContainer = AppColors.BgTertiaryLight,
        onPrimaryContainer = AppColors.TextPrimaryLight,
        secondary = AppColors.BrandSecondary,
        onSecondary = AppColors.TextPrimaryLight,
        secondaryContainer = AppColors.BgSecondaryLight,
        onSecondaryContainer = AppColors.TextSecondaryLight,
        tertiary = AppColors.StatusPro,
        onTertiary = AppColors.TextInverse,
        tertiaryContainer = Color(0xFFFEF3C7), // Amber 100
        onTertiaryContainer = Color(0xFF78350F), // Amber 900
        background = AppColors.BgSecondaryLight,
        onBackground = AppColors.TextPrimaryLight,
        surface = AppColors.SurfaceLight,
        onSurface = AppColors.TextPrimaryLight,
        surfaceVariant = AppColors.BgTertiaryLight,
        onSurfaceVariant = AppColors.TextSecondaryLight,
        outline = AppColors.OutlineLight,
        outlineVariant = Color(0xFFF1F5F9), // Slate 100
        error = AppColors.StatusError,
        onError = AppColors.TextInverse,
        errorContainer = Color(0xFFFEE2E2), // Red 100
        onErrorContainer = Color(0xFF991B1B), // Red 800
        inverseSurface = AppColors.BgPrimaryDark,
        inverseOnSurface = AppColors.TextPrimaryDark,
        inversePrimary = AppColors.BrandSecondary,
        scrim = Color(0xFF0F172A)
    )

val DarkColorScheme =
    darkColorScheme(
        primary = Color(0xFFF8FAFC), // Slate 50 (inverted for dark)
        onPrimary = AppColors.BrandPrimary,
        primaryContainer = AppColors.BgTertiaryDark,
        onPrimaryContainer = AppColors.TextPrimaryDark,
        secondary = AppColors.BgTertiaryDark,
        onSecondary = AppColors.TextPrimaryDark,
        secondaryContainer = AppColors.BgSecondaryDark,
        onSecondaryContainer = AppColors.TextSecondaryDark,
        tertiary = AppColors.StatusPro,
        onTertiary = Color(0xFF1C1C1C),
        tertiaryContainer = Color(0xFF78350F), // Amber 900
        onTertiaryContainer = Color(0xFFFEF3C7), // Amber 100
        background = AppColors.BgPrimaryDark,
        onBackground = AppColors.TextPrimaryDark,
        surface = AppColors.SurfaceDark,
        onSurface = AppColors.TextPrimaryDark,
        surfaceVariant = AppColors.BgTertiaryDark,
        onSurfaceVariant = AppColors.TextSecondaryDark,
        outline = AppColors.OutlineDark,
        outlineVariant = Color(0xFF334155), // Slate 700
        error = Color(0xFFF87171), // Red 400 (lighter for dark mode)
        onError = Color(0xFF450A0A), // Red 950
        errorContainer = Color(0xFF7F1D1D), // Red 900
        onErrorContainer = Color(0xFFFECACA), // Red 200
        inverseSurface = AppColors.BgPrimaryLight,
        inverseOnSurface = AppColors.TextPrimaryLight,
        inversePrimary = AppColors.BrandPrimary,
        scrim = Color(0xFF000000)
    )

// ==========================================================================
// EXTENDED COLORS (For custom status/semantic colors)
// ==========================================================================

data class ExtendedColors(
    val success: Color,
    val onSuccess: Color,
    val successContainer: Color,
    val onSuccessContainer: Color,
    val warning: Color,
    val onWarning: Color,
    val warningContainer: Color,
    val onWarningContainer: Color,
    val pro: Color,
    val onPro: Color,
    val textMuted: Color
)

val LightExtendedColors =
    ExtendedColors(
        success = AppColors.StatusSuccess,
        onSuccess = AppColors.TextInverse,
        successContainer = Color(0xFFDCFCE7), // Green 100
        onSuccessContainer = Color(0xFF14532D), // Green 900
        warning = AppColors.StatusWarning,
        onWarning = AppColors.TextInverse,
        warningContainer = Color(0xFFFEF3C7), // Amber 100
        onWarningContainer = Color(0xFF78350F), // Amber 900
        pro = AppColors.StatusPro,
        onPro = Color(0xFF1C1C1C),
        textMuted = AppColors.TextMutedLight
    )

val DarkExtendedColors =
    ExtendedColors(
        success = Color(0xFF4ADE80), // Green 400
        onSuccess = Color(0xFF052E16), // Green 950
        successContainer = Color(0xFF166534), // Green 800
        onSuccessContainer = Color(0xFFBBF7D0), // Green 200
        warning = Color(0xFFFBBF24), // Amber 400
        onWarning = Color(0xFF1C1C1C),
        warningContainer = Color(0xFF92400E), // Amber 800
        onWarningContainer = Color(0xFFFDE68A), // Amber 200
        pro = AppColors.StatusPro,
        onPro = Color(0xFF1C1C1C),
        textMuted = AppColors.TextMutedDark
    )

val LocalExtendedColors = staticCompositionLocalOf { LightExtendedColors }