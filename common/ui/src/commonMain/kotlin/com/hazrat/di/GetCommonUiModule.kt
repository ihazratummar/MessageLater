package com.hazrat.di

import com.hazrat.permissions.MokoPermissionManager
import com.hazrat.permissions.PermissionManager
import dev.icerock.moko.permissions.PermissionsController
import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 11/01/26
 */

fun getCommonUiModule(): Module = module {
    module {
        factory<PermissionManager> {params ->
            val controller = params.get<PermissionsController>()
            MokoPermissionManager(controller = controller)
        }
    }
}