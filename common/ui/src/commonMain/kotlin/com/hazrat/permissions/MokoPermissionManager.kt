package com.hazrat.permissions

import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController


/**
 * @author hazratummar
 * Created on 11/01/26
 */

class MokoPermissionManager (
    private val controller: PermissionsController
): PermissionManager {
    override suspend fun isGranted(permission: Permission): Boolean {
        return controller.isPermissionGranted(permission)
    }

    override suspend fun request(permission: Permission): Boolean {
        return try {
            controller.providePermission(permission)
            true
        }catch (_ : DeniedAlwaysException ){
            openSettings()
            false
        }catch (_ : DeniedException){
            controller.providePermission(permission)
            true
        }
    }

    override fun openSettings() {
        controller.openAppSettings()
    }

}