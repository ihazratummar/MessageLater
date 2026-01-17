package com.hazrat.messagelater.database.di

import android.content.Context
import com.hazrat.messagelater.database.AppDatabase
import com.hazrat.messagelater.database.SqlDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getDatabaseModule(): Module = module {
    single { SqlDriverFactory(context = get<Context>()).getSqlDriver() }
    single { AppDatabase.invoke(driver = get()) }
}