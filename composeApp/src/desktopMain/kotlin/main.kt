import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.di.dataModule
import domain.di.domainModule
import org.koin.core.context.startKoin
import presentation.di.presentationModule

fun main() = application {
    startKoin()

    Window(onCloseRequest = ::exitApplication, title = "AndroidProjectGenerator") {
        App()
    }
}

private fun startKoin() {
    startKoin {
        modules(
            dataModule,
            domainModule,
            presentationModule
        )
    }
}