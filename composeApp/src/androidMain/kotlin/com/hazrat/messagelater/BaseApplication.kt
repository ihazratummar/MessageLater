package com.hazrat.messagelater

import android.app.Application
import com.hazrat.messagelater.di.initKoin
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 08/01/26
 */

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            it.modules(
                module {
                    single { this@BaseApplication.applicationContext }
                }
            )
        }
    }

}