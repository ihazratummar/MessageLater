package com.hazrat.messagelater.database

import app.cash.sqldelight.db.SqlDriver


/**
 * @author hazratummar
 * Created on 08/01/26
 */

expect class SqlDriverFactory(
    context: Any ? = null
) {
    fun getSqlDriver() : SqlDriver

}