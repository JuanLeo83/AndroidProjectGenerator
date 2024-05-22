package presentation.screen.component

import androidprojectgenerator.composeapp.generated.resources.Res
import androidprojectgenerator.composeapp.generated.resources.ic_warning
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import extension.EMPTY_STRING
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FormField(
    modifier: Modifier = Modifier,
    data: FormFieldViewData,
    onValueChange: (String) -> Unit
) {
    with(data) {
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().then(modifier),
                value = value,
                onValueChange = onValueChange,
                maxLines = 1,
                label = { Text(text = label) },
                isError = errorMessage != null
            )
            errorMessage?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(Res.drawable.ic_warning),
                        contentDescription = String.EMPTY_STRING,
                        modifier = Modifier.size(24.dp).padding(2.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = it, color = Color.Red, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

data class FormFieldViewData(
    val label: String = String.EMPTY_STRING,
    val value: String = String.EMPTY_STRING,
    val errorMessage: String? = null,
    val isValid: Boolean = false
)