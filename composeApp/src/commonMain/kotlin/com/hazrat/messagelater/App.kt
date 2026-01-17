package com.hazrat.messagelater

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.hazrat.messagelater.navigation.AppNavigation
import com.hazrat.ui.MessageLaterTheme
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory

@Composable
fun App() {
    MessageLaterTheme {
        val navHostController = rememberNavController()

        val mokoFactory = rememberPermissionsControllerFactory()
        val controller = remember(mokoFactory){
            mokoFactory.createPermissionsController()
        }

        BindEffect(controller)

        AppNavigation(
            modifier = Modifier.fillMaxSize(),
            navHostController = navHostController,
            permissionsController = controller
        )
    }
}