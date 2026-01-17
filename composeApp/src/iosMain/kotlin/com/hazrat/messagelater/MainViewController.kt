package com.hazrat.messagelater

import androidx.compose.ui.window.ComposeUIViewController
import com.hazrat.messagelater.di.initKoin

fun MainViewController() = ComposeUIViewController (
    configure = {
        initKoin()
    }
){ App() }