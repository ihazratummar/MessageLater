import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowSizeClass

@Composable
fun rememberAdaptiveTypography(windowSizeClass: WindowSizeClass): Typography {
    val widthDp = windowSizeClass.minWidthDp

    return remember(widthDp) {
        val scale =
            when {
                widthDp < 600 -> 1.0f // Compact
                widthDp < 840 -> 1.05f // Medium
                else -> 1.1f // Expanded
            }

        val fontFamily = FontFamily.Default

        Typography(
            displayLarge =
                TextStyle(
                    fontFamily = fontFamily,
                    fontSize = (57 * scale).sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = (-0.25).sp,
                    lineHeight = (64 * scale).sp
                ),
            displayMedium =
                TextStyle(
                    fontFamily = fontFamily,
                    fontSize = (45 * scale).sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.sp,
                    lineHeight = (52 * scale).sp
                ),
            displaySmall =
                TextStyle(
                    fontFamily = fontFamily,
                    fontSize = (36 * scale).sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.sp,
                    lineHeight = (44 * scale).sp
                ),
            headlineLarge =
                TextStyle(
                    fontFamily = fontFamily,
                    fontSize = (32 * scale).sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.sp,
                    lineHeight = (40 * scale).sp
                ),
            headlineMedium =
                TextStyle(
                    fontFamily = fontFamily,
                    fontSize = (28 * scale).sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.sp,
                    lineHeight = (36 * scale).sp
                ),
            headlineSmall =
                TextStyle(
                    fontFamily = fontFamily,
                    fontSize = (24 * scale).sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.sp,
                    lineHeight = (32 * scale).sp
                ),
            titleLarge =
                TextStyle(
                    fontFamily = fontFamily,
                    fontSize = (22 * scale).sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.sp,
                    lineHeight = (28 * scale).sp
                ),
            titleMedium =
                TextStyle(
                    fontFamily = fontFamily,
                    fontSize = (16 * scale).sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.15.sp,
                    lineHeight = (24 * scale).sp
                ),
            titleSmall =
                TextStyle(
                    fontFamily = fontFamily,
                    fontSize = (14 * scale).sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.1.sp,
                    lineHeight = (20 * scale).sp
                ),
            bodyLarge =
                TextStyle(
                    fontFamily = fontFamily,
                    fontSize = (16 * scale).sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.5.sp,
                    lineHeight = (24 * scale).sp
                ),
            bodyMedium =
                TextStyle(
                    fontFamily = fontFamily,
                    fontSize = (14 * scale).sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.25.sp,
                    lineHeight = (20 * scale).sp
                ),
            bodySmall =
                TextStyle(
                    fontFamily = fontFamily,
                    fontSize = (12 * scale).sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.4.sp,
                    lineHeight = (16 * scale).sp
                ),
            labelLarge =
                TextStyle(
                    fontFamily = fontFamily,
                    fontSize = (14 * scale).sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.1.sp,
                    lineHeight = (20 * scale).sp
                ),
            labelMedium =
                TextStyle(
                    fontFamily = fontFamily,
                    fontSize = (12 * scale).sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp,
                    lineHeight = (16 * scale).sp
                ),
            labelSmall =
                TextStyle(
                    fontFamily = fontFamily,
                    fontSize = (11 * scale).sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp,
                    lineHeight = (16 * scale).sp
                )
        )
    }
}

// ==========================================================================
// SEMANTIC TEXT STYLE ALIASES
// ==========================================================================

object AppTextStyles {
    val screenTitle: TextStyle
        @Composable get() = MaterialTheme.typography.titleLarge

    val sectionHeader: TextStyle
        @Composable
        get() =
            MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )

    val cardTitle: TextStyle
        @Composable
        get() = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)

    val cardSubtitle: TextStyle
        @Composable get() = MaterialTheme.typography.bodyMedium

    val body: TextStyle
        @Composable get() = MaterialTheme.typography.bodyLarge

    val bodySecondary: TextStyle
        @Composable get() = MaterialTheme.typography.bodyMedium

    val button: TextStyle
        @Composable get() = MaterialTheme.typography.labelLarge

    val caption: TextStyle
        @Composable get() = MaterialTheme.typography.labelMedium

    val overline: TextStyle
        @Composable
        get() =
            MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

    val input: TextStyle
        @Composable get() = MaterialTheme.typography.bodyLarge

    val inputLabel: TextStyle
        @Composable get() = MaterialTheme.typography.bodySmall
}

// ==========================================================================
// THEME COMPOSABLE (Using Official WindowSizeClass API)
// ==========================================================================

val LocalWindowSizeClass =
    staticCompositionLocalOf<WindowSizeClass> { error("WindowSizeClass not provided") }
