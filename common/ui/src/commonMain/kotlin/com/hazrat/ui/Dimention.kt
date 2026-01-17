package com.hazrat.ui

import androidx.window.core.layout.WindowSizeClass


/**
 * @author hazratummar
 * Created on 10/01/26
 */

//
// Dimensions.kt
// Adaptive spacing, sizing, and dimension tokens for all screen sizes
//


import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ==========================================================================
// ADAPTIVE DIMENSIONS
// ==========================================================================

/**
 * Dimension tokens that scale based on window size class. All values are in Dp and adapt for
 * Compact, Medium, and Expanded layouts.
 */


@Immutable
data class AppDimensions(
    // Spacing (padding, margins, gaps)
    val spacingXxs: Dp, // 2-4dp
    val spacingXs: Dp, // 4-6dp
    val spacingSm: Dp, // 8-10dp
    val spacingMd: Dp, // 12-16dp
    val spacingLg: Dp, // 16-20dp
    val spacingXl: Dp, // 24-32dp
    val spacingXxl: Dp, // 32-48dp

    // Content padding
    val screenPaddingHorizontal: Dp,
    val screenPaddingVertical: Dp,
    val cardPadding: Dp,
    val listItemPadding: Dp,

    // Component sizes
    val buttonHeight: Dp,
    val buttonHeightSmall: Dp,
    val buttonHeightLarge: Dp,
    val iconSizeSmall: Dp,
    val iconSizeMedium: Dp,
    val iconSizeLarge: Dp,
    val avatarSizeSmall: Dp,
    val avatarSizeMedium: Dp,
    val avatarSizeLarge: Dp,

    // Touch targets (minimum 48dp for accessibility)
    val minTouchTarget: Dp,

    // Corner radius
    val radiusXs: Dp,
    val radiusSm: Dp,
    val radiusMd: Dp,
    val radiusLg: Dp,
    val radiusXl: Dp,
    val radiusFull: Dp,

    // Elevation
    val elevationNone: Dp,
    val elevationSm: Dp,
    val elevationMd: Dp,
    val elevationLg: Dp,

    // Layout constraints
    val maxContentWidth: Dp,
    val minCardWidth: Dp,
    val maxCardWidth: Dp,
    val minCardHeight: Dp,
    val maxCardHeight: Dp,


    // Divider/border
    val borderWidth: Dp,
    val dividerHeight: Dp,

    // FAB
    val fabSize: Dp,
    val fabSizeSmall: Dp,

    // Bottom sheet/dialog
    val bottomSheetPeekHeight: Dp,
    val dialogMaxWidth: Dp,

    // Navigation
    val appBarHeight: Dp,
    val bottomNavHeight: Dp,

    val size120: Dp
)

// ==========================================================================
// COMPACT DIMENSIONS (Phones < 600dp)
// ==========================================================================

val CompactDimensions =
    AppDimensions(
        // Spacing
        spacingXxs = 2.dp,
        spacingXs = 4.dp,
        spacingSm = 8.dp,
        spacingMd = 12.dp,
        spacingLg = 16.dp,
        spacingXl = 24.dp,
        spacingXxl = 32.dp,

        // Content padding
        screenPaddingHorizontal = 16.dp,
        screenPaddingVertical = 16.dp,
        cardPadding = 16.dp,
        listItemPadding = 12.dp,

        // Component sizes
        buttonHeight = 48.dp,
        buttonHeightSmall = 36.dp,
        buttonHeightLarge = 56.dp,
        iconSizeSmall = 16.dp,
        iconSizeMedium = 24.dp,
        iconSizeLarge = 32.dp,
        avatarSizeSmall = 32.dp,
        avatarSizeMedium = 40.dp,
        avatarSizeLarge = 56.dp,

        // Touch targets
        minTouchTarget = 48.dp,

        // Corner radius
        radiusXs = 4.dp,
        radiusSm = 8.dp,
        radiusMd = 12.dp,
        radiusLg = 16.dp,
        radiusXl = 24.dp,
        radiusFull = 9999.dp,

        // Elevation
        elevationNone = 0.dp,
        elevationSm = 2.dp,
        elevationMd = 4.dp,
        elevationLg = 8.dp,

        // Layout constraints
        maxContentWidth = 600.dp,
        minCardWidth = 280.dp,
        maxCardWidth = 400.dp,
        minCardHeight = 200.dp,
        maxCardHeight = 400.dp,


        // Divider/border
        borderWidth = 1.dp,
        dividerHeight = 1.dp,

        // FAB
        fabSize = 56.dp,
        fabSizeSmall = 40.dp,

        // Bottom sheet/dialog
        bottomSheetPeekHeight = 56.dp,
        dialogMaxWidth = 320.dp,

        // Navigation
        appBarHeight = 56.dp,
        bottomNavHeight = 80.dp,

        // Custom DP
        size120 = 120.dp
    )

// ==========================================================================
// MEDIUM DIMENSIONS (Tablets portrait, foldables 600-840dp)
// ==========================================================================

val MediumDimensions =
    AppDimensions(
        // Spacing (slightly larger)
        spacingXxs = 3.dp,
        spacingXs = 6.dp,
        spacingSm = 10.dp,
        spacingMd = 14.dp,
        spacingLg = 18.dp,
        spacingXl = 28.dp,
        spacingXxl = 40.dp,

        // Content padding (more breathing room)
        screenPaddingHorizontal = 24.dp,
        screenPaddingVertical = 20.dp,
        cardPadding = 20.dp,
        listItemPadding = 14.dp,

        // Component sizes
        buttonHeight = 52.dp,
        buttonHeightSmall = 40.dp,
        buttonHeightLarge = 60.dp,
        iconSizeSmall = 18.dp,
        iconSizeMedium = 26.dp,
        iconSizeLarge = 36.dp,
        avatarSizeSmall = 36.dp,
        avatarSizeMedium = 48.dp,
        avatarSizeLarge = 64.dp,

        // Touch targets
        minTouchTarget = 48.dp,

        // Corner radius
        radiusXs = 6.dp,
        radiusSm = 10.dp,
        radiusMd = 14.dp,
        radiusLg = 20.dp,
        radiusXl = 28.dp,
        radiusFull = 9999.dp,

        // Elevation
        elevationNone = 0.dp,
        elevationSm = 2.dp,
        elevationMd = 6.dp,
        elevationLg = 10.dp,

        // Layout constraints
        maxContentWidth = 720.dp,
        minCardWidth = 320.dp,
        maxCardWidth = 480.dp,
        minCardHeight = 240.dp,
        maxCardHeight = 480.dp,



        // Divider/border
        borderWidth = 1.dp,
        dividerHeight = 1.dp,

        // FAB
        fabSize = 64.dp,
        fabSizeSmall = 48.dp,

        // Bottom sheet/dialog
        bottomSheetPeekHeight = 64.dp,
        dialogMaxWidth = 400.dp,

        // Navigation
        appBarHeight = 64.dp,
        bottomNavHeight = 88.dp,

        // Custom DP
        size120 = 132.dp
    )

// ==========================================================================
// EXPANDED DIMENSIONS (Tablets landscape, desktop > 840dp)
// ==========================================================================

val ExpandedDimensions =
    AppDimensions(
        // Spacing (generous)
        spacingXxs = 4.dp,
        spacingXs = 8.dp,
        spacingSm = 12.dp,
        spacingMd = 16.dp,
        spacingLg = 24.dp,
        spacingXl = 32.dp,
        spacingXxl = 48.dp,

        // Content padding
        screenPaddingHorizontal = 32.dp,
        screenPaddingVertical = 24.dp,
        cardPadding = 24.dp,
        listItemPadding = 16.dp,

        // Component sizes
        buttonHeight = 56.dp,
        buttonHeightSmall = 44.dp,
        buttonHeightLarge = 64.dp,
        iconSizeSmall = 20.dp,
        iconSizeMedium = 28.dp,
        iconSizeLarge = 40.dp,
        avatarSizeSmall = 40.dp,
        avatarSizeMedium = 56.dp,
        avatarSizeLarge = 72.dp,

        // Touch targets
        minTouchTarget = 48.dp,

        // Corner radius
        radiusXs = 8.dp,
        radiusSm = 12.dp,
        radiusMd = 16.dp,
        radiusLg = 24.dp,
        radiusXl = 32.dp,
        radiusFull = 9999.dp,

        // Elevation
        elevationNone = 0.dp,
        elevationSm = 3.dp,
        elevationMd = 8.dp,
        elevationLg = 12.dp,

        // Layout constraints
        maxContentWidth = 1040.dp,
        minCardWidth = 360.dp,
        maxCardWidth = 560.dp,
        minCardHeight = 280.dp,
        maxCardHeight = 480.dp,



        // Divider/border
        borderWidth = 1.dp,
        dividerHeight = 1.dp,

        // FAB
        fabSize = 72.dp,
        fabSizeSmall = 56.dp,

        // Bottom sheet/dialog
        bottomSheetPeekHeight = 72.dp,
        dialogMaxWidth = 560.dp,

        // Navigation
        appBarHeight = 72.dp,
        bottomNavHeight = 96.dp,

        // Custom DP
        size120 = 144.dp
    )

// ==========================================================================
// COMPOSITION LOCAL
// ==========================================================================

val LocalAppDimensions = staticCompositionLocalOf { CompactDimensions }

/** Returns the appropriate dimensions based on window size class. */
fun dimensionsFor(windowSizeClass: WindowSizeClass): AppDimensions {
    val widthDp = windowSizeClass.minWidthDp
    return when {
        widthDp < 600 -> CompactDimensions
        widthDp < 840 -> MediumDimensions
        else -> ExpandedDimensions
    }
}

// ==========================================================================
// CONVENIENCE OBJECT FOR ACCESSING DIMENSIONS
// ==========================================================================

/**
 * Access current dimensions from anywhere within MessageLaterTheme.
 *
 * Usage:
 * ```
 * Box(
 *     modifier = Modifier.padding(AppDimens.screenPaddingHorizontal)
 * )
 * ```
 */
object AppDimens {
    val current: AppDimensions
        @Composable get() = LocalAppDimensions.current

    // Quick access to common values
    val spacingXxs: Dp
        @Composable get() = current.spacingXxs
    val spacingXs: Dp
        @Composable get() = current.spacingXs
    val spacingSm: Dp
        @Composable get() = current.spacingSm
    val spacingMd: Dp
        @Composable get() = current.spacingMd
    val spacingLg: Dp
        @Composable get() = current.spacingLg
    val spacingXl: Dp
        @Composable get() = current.spacingXl
    val spacingXxl: Dp
        @Composable get() = current.spacingXxl

    val screenPaddingHorizontal: Dp
        @Composable get() = current.screenPaddingHorizontal
    val screenPaddingVertical: Dp
        @Composable get() = current.screenPaddingVertical
    val cardPadding: Dp
        @Composable get() = current.cardPadding


    val buttonHeight: Dp
        @Composable get() = current.buttonHeight
    val minTouchTarget: Dp
        @Composable get() = current.minTouchTarget
    val buttonHeightSmall: Dp
        @Composable get() = current.buttonHeightSmall
    val buttonHeightLarge: Dp
        @Composable get() = current.buttonHeightLarge


    val radiusXs: Dp
        @Composable get() = current.radiusXs
    val radiusSm: Dp
        @Composable get() = current.radiusSm
    val radiusMd: Dp
        @Composable get() = current.radiusMd
    val radiusLg: Dp
        @Composable get() = current.radiusLg
    val radiusXl : Dp
        @Composable get() = current.radiusXl

    val radiusFull: Dp
        @Composable get() = current.radiusFull

    val iconSizeSmall: Dp
        @Composable get() = current.iconSizeSmall
    val iconSizeMedium: Dp
        @Composable get() = current.iconSizeMedium
    val iconSizeLarge: Dp
        @Composable get() = current.iconSizeLarge

    val avatarSizeSmall: Dp
        @Composable get() = current.avatarSizeSmall
    val avatarSizeMedium: Dp
        @Composable get() = current.avatarSizeMedium
    val avatarSizeLarge: Dp
        @Composable get() = current.avatarSizeLarge

    val maxContentWidth: Dp
        @Composable get() = current.maxContentWidth
    val fabSize: Dp
        @Composable get() = current.fabSize
    val appBarHeight: Dp
        @Composable get() = current.appBarHeight
    val fabSizeSmall: Dp
        @Composable get() = current.fabSizeSmall

    val size120: Dp
        @Composable get() = current.size120

    val listItemPadding: Dp
        @Composable get() = current.listItemPadding
    val bottomNavHeight: Dp
        @Composable get() = current.bottomNavHeight

    val minCardHeight: Dp
        @Composable get() = current.minCardHeight

    val borderWidth: Dp
        @Composable get() = current.borderWidth
    val dividerHeight: Dp
        @Composable get() = current.dividerHeight

    val elevationNone: Dp
        @Composable get() = current.elevationNone
    val elevationSm: Dp
        @Composable get() = current.elevationSm

    val elevationMd: Dp
        @Composable get() = current.elevationMd
    val elevationLg: Dp
        @Composable get() = current.elevationLg


}
