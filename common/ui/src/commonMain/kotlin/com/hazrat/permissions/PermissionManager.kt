package com.hazrat.permissions

import dev.icerock.moko.permissions.Permission


/**
 * @author hazratummar
 * Created on 11/01/26
 */

interface PermissionManager {

    suspend fun isGranted(permission: Permission) : Boolean

    suspend fun request(permission: Permission) : Boolean

    fun openSettings()

}