package com.sandeep03edu.passwordmanager.manager.di

import com.sandeep03edu.passwordmanager.manager.credentials.domain.CredentialDataSource

expect class AppModule {
    val credentialDataSource: CredentialDataSource
}