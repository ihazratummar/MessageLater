package com.hazrat.messagelater.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hazrat.create_reminder.ui.CreateReminderScreen
import com.hazrat.create_reminder.ui.CreateReminderScreenViewModel
import com.hazrat.dashboard.ui.DashboardScreen
import com.hazrat.permissions.MokoPermissionManager
import dev.icerock.moko.permissions.PermissionsController
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


/**
 * @author hazratummar
 * Created on 10/01/26
 */

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    permissionsController: PermissionsController

) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Dest.Dashboard
    ) {
        composable<Dest.Dashboard> {
            DashboardScreen(
                onSettingIconClick = {
                    navHostController.navigate(Dest.Settings)
                },
                onFabClick = {
                    navHostController.navigate(Dest.CreateReminder)
                }
            )
        }

        composable <Dest.CreateReminder>{
            val viewModel = koinViewModel<CreateReminderScreenViewModel>(
                parameters = {
                    parametersOf(permissionsController, MokoPermissionManager(controller = permissionsController))
                }
            )
            val state by viewModel.state.collectAsStateWithLifecycle()

            CreateReminderScreen(
                onBackClick = {
                    navHostController.popBackStack()
                },
                onProClick = {

                },
                event = viewModel::onEvent,
                state = state,
                effect = viewModel.effect,
            )
        }
    }
}