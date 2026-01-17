package com.hazrat.contacts

import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getContactsModule(): Module = module {
    single { ContactManager() }
}