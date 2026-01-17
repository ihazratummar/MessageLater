package com.hazrat.messagelater.di

import com.hazrat.common.data.di.getCommonDataModule
import com.hazrat.contacts.getContactsModule
import com.hazrat.create_reminder.ui.di.getCreateReminderUiModule
import com.hazrat.di.getCommonUiModule
import com.hazrat.messagelater.database.di.getDatabaseModule
import com.hazrat.notification.getNotificationModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin


/**
 * @author hazratummar
 * Created on 08/01/26
 */

fun initKoin(koinApplication: ((KoinApplication) -> Unit)? = null){
    startKoin {
        koinApplication?.invoke(this)
        modules(
            getDatabaseModule(),
            getCommonUiModule(),
            getCreateReminderUiModule(),
            getContactsModule(),
            getNotificationModule(),
            getCommonDataModule()
        )
    }
}