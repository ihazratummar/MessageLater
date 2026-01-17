package com.hazrat.notification

import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getNotificationModule(): Module = module{

    single { LocalNotificationManager(context = get()) }

}