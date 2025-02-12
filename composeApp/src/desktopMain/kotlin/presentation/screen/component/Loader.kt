package presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Loader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0x77000000)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}