import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.salman.news.data.di.PlatformDataModule
import com.salman.news.di.sharedModule
import com.salman.news.presentation.AppEntryContent
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent.inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/26/2024.
 */
fun main() {
    startKoin {
        modules(sharedModule + PlatformDataModule().module)
    }
    val appEntryPoint: AppEntryContent by inject(AppEntryContent::class.java)
    application {
        Window(
            title = "News",
            onCloseRequest = ::exitApplication,
        ) {
            appEntryPoint.Content()
        }
    }
}