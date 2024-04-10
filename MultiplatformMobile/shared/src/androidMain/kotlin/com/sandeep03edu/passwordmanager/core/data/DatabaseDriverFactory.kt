package com.sandeep03edu.passwordmanager.core.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.sandeep03edu.passwordmanager.database.CredentialDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            schema = CredentialDatabase.Schema,
            context= context,
            name = "CredentialDatabase.db",
        )
    }
}