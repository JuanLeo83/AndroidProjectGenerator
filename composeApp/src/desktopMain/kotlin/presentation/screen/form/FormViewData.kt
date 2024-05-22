package presentation.screen.form

import presentation.screen.component.FormFieldViewData
import strings.Texts

data class FormViewData(
    val isLoading: Boolean = false,
    val projectNameField: FormFieldViewData = FormFieldViewData(label = Texts.res.projectNameLabel),
    val packageNameField: FormFieldViewData = FormFieldViewData(label = Texts.res.packageNameLabel),
    val oldPackageNameField: FormFieldViewData = FormFieldViewData(
        label = Texts.res.oldPackageNameLabel,
        value = "com.jgpl.templateapp"
    ),
    val destinationPathField: FormFieldViewData = FormFieldViewData(
        label = Texts.res.destinationPathLabel,
        value = "C:\\Users\\juanl\\Tests",
        isValid = true
    ),
    val templateUrlField: FormFieldViewData = FormFieldViewData(
        label = Texts.res.templateUrlLabel,
        value = "https://github.com/JuanLeo83/AndroidTemplateApp.git",
        isValid = true
    ),
    val buttonText: String = Texts.res.generateProjectButton,
    val enableButton: Boolean = false,
    val errorMessage: String = Texts.res.unknownError
) {
    fun refresh() = copy(
        projectNameField = projectNameField.copy(label = Texts.res.projectNameLabel),
        packageNameField = packageNameField.copy(label = Texts.res.packageNameLabel),
        destinationPathField = destinationPathField.copy(label = Texts.res.destinationPathLabel),
        templateUrlField = templateUrlField.copy(label = Texts.res.templateUrlLabel),
        buttonText = Texts.res.generateProjectButton
    )
}