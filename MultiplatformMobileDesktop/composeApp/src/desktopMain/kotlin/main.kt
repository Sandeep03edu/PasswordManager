import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.sandeep03edu.passwordmanager.App
import com.sandeep03edu.passwordmanager.manager.di.AppModule

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Password Manager") {
        App(darkTheme = isSystemInDarkTheme(),
            dynamicColor = false,
            appModule = AppModule()
        )
    }
}