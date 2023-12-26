package com.sandeep03edu.passwordmanager.manager.authentication.presentation

import dev.icerock.moko.biometry.BiometryAuthenticator
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.launch

class BiometryViewModel(
    val biometryAuthenticator: BiometryAuthenticator,
    override val eventsDispatcher: EventsDispatcher<EventListener>,
) : ViewModel(), EventsDispatcherOwner<BiometryViewModel.EventListener> {

    @Suppress("TooGenericExceptionCaught")
    fun tryToAuth() {
        viewModelScope.launch {
            try {
                val isSuccess = biometryAuthenticator.checkBiometryAuthentication(
                    requestTitle = "Biometry".desc(),
                    requestReason = "Just for test".desc(),
                    failureButtonText = "Oops".desc()
                )
                if (isSuccess) {
                    eventsDispatcher.dispatchEvent { onSuccess() }
                }
            } catch (throwable: Throwable) {
                println(throwable)
                eventsDispatcher.dispatchEvent { onFail() }
            }
        }
    }

    interface EventListener {
        fun onSuccess()

        fun onFail()
    }
}
