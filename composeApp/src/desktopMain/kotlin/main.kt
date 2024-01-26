import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.salman.news.data.di.dataModule
import com.salman.news.presentation.App
import com.salman.news.presentation.sharedModule
import org.koin.core.context.GlobalContext.startKoin

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/26/2024.
 */
fun main() {
    startKoin {
        modules(sharedModule + dataModule)
    }

    application {
        Window(
            title = "Movies-KMP",
            onCloseRequest = ::exitApplication,
        ) {
            App()
        }
    }
}