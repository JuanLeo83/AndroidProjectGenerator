import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screen.form.FormScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(screen = FormScreen()) {
            FadeTransition(it)
        }
    }
}