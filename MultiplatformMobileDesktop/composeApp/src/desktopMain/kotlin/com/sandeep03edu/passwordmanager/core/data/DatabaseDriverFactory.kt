package com.sandeep03edu.passwordmanager.core.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.sandeep03edu.passwordmanager.database.CredentialDatabase
import java.util.Properties

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(url = "jdbc:sqlite:CredentialDatabase.db")
//        CredentialDatabase.Schema.create(driver)
        return driver
    }
}