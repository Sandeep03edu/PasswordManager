import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.sandeep03edu.passwordmanager.App
import com.sandeep03edu.passwordmanager.manager.di.AppModule

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Password Manager",
        icon = BitmapPainter(useResource("ic_launcher.png", ::loadImageBitmap)),
        ) {
        App(
            darkTheme = isSystemInDarkTheme(),
            dynamicColor = false,
            appModule = AppModule()
        )
    }
}