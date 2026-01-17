package com.hazrat.create_reminder.ui.di


import com.hazrat.common.domain.repository.ReminderRepository
import com.hazrat.create_reminder.ui.CreateReminderScreenViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

/**
 * @author hazratummar
 * Created on 11/01/26
 */

fun getCreateReminderUiModule() : Module = module {
    viewModel { params ->
        CreateReminderScreenViewModel(
            permissionManager = params.get(),
            contactManager = get(),
            reminderRepository = get<ReminderRepository>()
        )
    }
}