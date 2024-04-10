package com.sandeep03edu.passwordmanager.core.data

import com.sandeep03edu.passwordmanager.database.CredentialDatabase

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(CredentialDatabase.Schema, "CredentialDatabase.db")
    }
}