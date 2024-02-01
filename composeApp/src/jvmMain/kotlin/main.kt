import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.salman.news.data.di.PlatformDataModule
import com.salman.news.di.sharedModule
import com.salman.news.presentation.App
import org.koin.core.context.GlobalContext.startKoin

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/26/2024.
 */
fun main() {
    startKoin {
        modules(sharedModule + PlatformDataModule().module)
    }

    application {
        Window(
            title = "News",
            onCloseRequest = ::exitApplication,
        ) {
            App()
        }
    }
}