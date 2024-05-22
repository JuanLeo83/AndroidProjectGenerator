package presentation.screen.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import presentation.screen.component.FormField
import presentation.screen.component.Loader
import strings.Texts
import strings.en
import strings.es

class FormScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<FormViewModel>()
        val state by viewModel.state.collectAsState()

        handleScreenState(viewModel, state)
    }

    @Composable
    private fun handleScreenState(viewModel: FormViewModel, state: FormScreenState) {
        val interactionSource = remember { MutableInteractionSource() }
        val focusManager = LocalFocusManager.current

        Box(modifier = Modifier.fillMaxSize().clickable(
            interactionSource = interactionSource,
            indication = null
        ) { focusManager.clearFocus() }) {
            when (state) {
                is FormScreenState.Initial,
                is FormScreenState.FillingForm -> FormComponent(viewModel, state.viewData)

                is FormScreenState.Success -> {
                    // navigate to success screen
                }

                is FormScreenState.Error -> {
                    // show error dialog
                }
            }
        }
    }

    @Composable
    private fun FormComponent(viewModel: FormViewModel, viewData: FormViewData) {
        var selectedLanguage by remember { mutableStateOf(Texts.res) }

        Column(
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(
                    text = Texts.res.english,
                    fontWeight = if (selectedLanguage == en) FontWeight.Bold else null,
                    modifier = Modifier.clickable {
                        Texts.res = en
                        selectedLanguage = en
                        viewModel.translate()
                    }
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = Texts.res.spanish,
                    fontWeight = if (selectedLanguage == es) FontWeight.Bold else null,
                    modifier = Modifier.clickable {
                        Texts.res = es
                        selectedLanguage = es
                        viewModel.translate()
                    }
                )
            }

            Spacer(modifier = Modifier.padding(24.dp))

            FormField(
                modifier = Modifier
                    .onFocusChanged { focusState ->
                        if (!focusState.isFocused) {
                            viewModel.validateProjectName()
                        }
                    },
                data = viewData.projectNameField,
                onValueChange = { viewModel.setProjectName(it) }
            )

            FormField(
                modifier = Modifier.onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        viewModel.validatePackageName()
                    }
                },
                data = viewData.packageNameField,
                onValueChange = { viewModel.setPackageName(it) },
            )

            FormField(
                modifier = Modifier.onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        viewModel.validatePackageName()
                    }
                },
                data = viewData.oldPackageNameField,
                onValueChange = { viewModel.setOldPackageName(it) },
            )

            FormField(
                data = viewData.destinationPathField,
                onValueChange = { viewModel.setDestinationPath(it) }
            )

            FormField(
                modifier = Modifier.onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        viewModel.validateTemplateUrl()
                    }
                },
                data = viewData.templateUrlField,
                onValueChange = { viewModel.setTemplateUrl(it) }
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Button(onClick = { viewModel.generateProject() }, enabled = viewData.enableButton) {
                Text(viewData.buttonText)
            }
        }
        if (viewData.isLoading) {
            Loader()
        }
    }
}