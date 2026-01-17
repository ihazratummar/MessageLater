package com.hazrat.messagelater.database.di

import com.hazrat.messagelater.database.AppDatabase
import com.hazrat.messagelater.database.SqlDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getDatabaseModule(): Module = module {
    single { SqlDriverFactory().getSqlDriver() }
    single { AppDatabase.invoke(get()) }
}