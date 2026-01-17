package com.hazrat.ui

import LocalWindowSizeClass
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.window.core.layout.WindowSizeClass
import rememberAdaptiveTypography


/**
 * @author hazratummar
 * Created on 10/01/26
 */


@Composable
fun MessageLaterTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val windowSizeClass = windowAdaptiveInfo.windowSizeClass

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors
    val typography = rememberAdaptiveTypography(windowSizeClass)
    val dimensions = dimensionsFor(windowSizeClass)

    CompositionLocalProvider(
        LocalExtendedColors provides extendedColors,
        LocalWindowSizeClass provides windowSizeClass,
        LocalAppDimensions provides dimensions

    ) {
        MaterialTheme(colorScheme = colorScheme, typography = typography, content = content)
    }
}


object MessageLaterTheme {
    val extendedColors: ExtendedColors
        @Composable get() = LocalExtendedColors.current

    val windowSizeClass: WindowSizeClass
        @Composable get() = LocalWindowSizeClass.current

    /** Width breakpoints: <600dp = Compact, 600-840dp = Medium, >840dp = Expanded */
    val isCompactWidth: Boolean
        @Composable get() = windowSizeClass.minWidthDp < 600

    val isMediumWidth: Boolean
        @Composable get() = windowSizeClass.minWidthDp in 600..839

    val isExpandedWidth: Boolean
        @Composable get() = windowSizeClass.minWidthDp >= 840

    /** Height breakpoints: <480dp = Compact, 480-900dp = Medium, >900dp = Expanded */
    val isCompactHeight: Boolean
        @Composable get() = windowSizeClass.minHeightDp < 480

    val isMediumHeight: Boolean
        @Composable get() = windowSizeClass.minHeightDp in 480..899

    val isExpandedHeight: Boolean
        @Composable get() = windowSizeClass.minHeightDp >= 900
}