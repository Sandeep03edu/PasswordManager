package com.sandeep03edu.passwordmanager.core.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.sandeep03edu.passwordmanager.database.CredentialDatabase

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(CredentialDatabase.Schema, "CredentialDatabase.db")
    }
}