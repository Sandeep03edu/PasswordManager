package com.sandeep03edu.passwordmanager.manager.di

import android.content.Context
import com.sandeep03edu.passwordmanager.core.data.DatabaseDriverFactory
import com.sandeep03edu.passwordmanager.database.CredentialDatabase
import com.sandeep03edu.passwordmanager.manager.credentials.data.SqlDelightCredentialDataSource
import com.sandeep03edu.passwordmanager.manager.credentials.domain.CredentialDataSource

actual class AppModule(context: Context) {
    actual val credentialDataSource: CredentialDataSource by lazy {
        SqlDelightCredentialDataSource(
            db = CredentialDatabase(
                driver = DatabaseDriverFactory(context).create()
            )
        )
    }
}