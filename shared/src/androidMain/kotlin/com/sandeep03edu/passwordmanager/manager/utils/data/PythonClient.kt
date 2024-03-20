package com.sandeep03edu.passwordmanager.manager.utils.data

import android.content.Context
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

// Android-specific implementation
actual class PythonClient (private val context: Context) {
    actual fun processQuery(query: String): String {
        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(context))
        }

        val python = Python.getInstance()
        val pythonFile = python.getModule("server")
        val response = pythonFile.callAttr("process_query", "user_input_here")


        val chatbotResponse = response.toString()
        return chatbotResponse
    }
}