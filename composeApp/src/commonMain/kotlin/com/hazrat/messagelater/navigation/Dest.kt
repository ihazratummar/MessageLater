package com.hazrat.messagelater.navigation

import kotlinx.serialization.Serializable


/**
 * @author hazratummar
 * Created on 10/01/26
 */

sealed interface Dest {

    @Serializable
    data object Dashboard : Dest

    @Serializable
    data object Settings : Dest

    @Serializable
    data object CreateReminder : Dest

}