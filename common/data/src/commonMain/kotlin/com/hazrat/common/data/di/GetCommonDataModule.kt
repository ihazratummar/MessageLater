package com.hazrat.common.data.di

import com.hazrat.common.data.repository.ReminderRepositoryImpl
import com.hazrat.common.domain.repository.ReminderRepository
import com.hazrat.messagelater.database.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 13/01/26
 */

fun getCommonDataModule(): Module = module {
    single<ReminderRepository> {
        ReminderRepositoryImpl(
            appDatabase = get<AppDatabase>(),
            notificationManager = get()
        )
    }
}