package presentation.screen.form

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.usecase.CloneGitRepositoryUseCase
import domain.usecase.ValidatePackageNameUseCase
import domain.usecase.ValidateProjectNameUseCase
import domain.usecase.ValidateTemplateUrlUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.screen.component.FormFieldViewData
import presentation.screen.form.FormScreenState.Initial
import strings.Texts

class FormViewModel(
    private val validateProjectNameUseCase: ValidateProjectNameUseCase,
    private val validatePackageNameUseCase: ValidatePackageNameUseCase,
    private val validateTemplateUrlUseCase: ValidateTemplateUrlUseCase,
    private val cloneGitRepositoryUseCase: CloneGitRepositoryUseCase,
    private val mapper: FormMapper
) : StateScreenModel<FormScreenState>(Initial()) {

    fun generateProject() {
        loading(true)

        screenModelScope.launch {
            val cloneRepositoryModel = mapper.mapToCloneRepositoryModel(mutableState.value.viewData)

            with(mutableState) {
                cloneGitRepositoryUseCase(cloneRepositoryModel).fold(
                    onSuccess = {
                        delay(1000)
                        loading(false)
                        value = FormScreenState.Success(mutableState.value.viewData)
                    },
                    onFailure = {
                        loading(false)
                        handleError(it.message ?: Texts.res.unknownError)
                    }
                )
            }
        }
    }

    fun setProjectName(value: String) {
        mutableState.value = FormScreenState.FillingForm(
            with(mutableState.value.viewData) {
                copy(projectNameField = projectNameField.copy(value = value.trim()))
            }
        )
        validateProjectName()
    }

    fun validateProjectName() {
        validateField { viewData ->
            validateProjectNameUseCase(viewData.projectNameField.value).fold(
                onSuccess = {
                    mutableState.value = FormScreenState.FillingForm(
                        viewData.copy(projectNameField = successField(viewData.projectNameField))
                    )
                    checkEnableButton()
                },
                onFailure = {
                    mutableState.value = FormScreenState.FillingForm(
                        viewData.copy(
                            projectNameField = failureField(
                                field = viewData.projectNameField,
                                errorMessage = mapper.mapProjectNameErrorMessage(it)
                            )
                        )
                    )
                    checkEnableButton()
                }
            )
        }
    }

    fun setPackageName(value: String) {
        mutableState.value = FormScreenState.FillingForm(
            with(mutableState.value.viewData) {
                copy(packageNameField = packageNameField.copy(value = value.trim()))
            }
        )
        validatePackageName()
    }

    fun setOldPackageName(value: String) {
        mutableState.value = FormScreenState.FillingForm(
            with(mutableState.value.viewData) {
                copy(oldPackageNameField = oldPackageNameField.copy(value = value.trim()))
            }
        )
        validatePackageName()
    }

    fun validatePackageName() {
        validateField { viewData ->
            validatePackageNameUseCase(viewData.packageNameField.value).fold(
                onSuccess = {
                    mutableState.value = FormScreenState.FillingForm(
                        viewData.copy(packageNameField = successField(viewData.packageNameField))
                    )
                    checkEnableButton()
                },
                onFailure = {
                    mutableState.value = FormScreenState.FillingForm(
                        viewData.copy(
                            packageNameField = failureField(
                                field = viewData.packageNameField,
                                errorMessage = mapper.mapPackageNameErrorMessage(it)
                            )
                        )
                    )
                    checkEnableButton()
                }
            )
        }
    }

    fun setDestinationPath(value: String) {
        mutableState.value = FormScreenState.FillingForm(
            with(mutableState.value.viewData) {
                copy(
                    destinationPathField = destinationPathField.copy(
                        value = value.trim(),
                        isValid = true
                    )
                )
            }
        )
    }

    fun setTemplateUrl(value: String) {
        mutableState.value = FormScreenState.FillingForm(
            with(mutableState.value.viewData) {
                copy(templateUrlField = templateUrlField.copy(value = value.trim()))
            }
        )
        validateTemplateUrl()
    }

    fun validateTemplateUrl() {
        validateField { viewData ->
            validateTemplateUrlUseCase(viewData.templateUrlField.value).fold(
                onSuccess = {
                    mutableState.value = FormScreenState.FillingForm(
                        viewData.copy(templateUrlField = successField(viewData.templateUrlField))
                    )
                    checkEnableButton()
                },
                onFailure = {
                    mutableState.value = FormScreenState.FillingForm(
                        viewData.copy(
                            templateUrlField = failureField(
                                field = viewData.templateUrlField,
                                errorMessage = mapper.mapTemplateUrlErrorMessage(it)
                            )
                        )
                    )
                    checkEnableButton()
                }
            )
        }
    }

    fun translate() {
        mutableState.value = mutableState.value.clone(
            mutableState.value.viewData.refresh()
        )
    }

    private fun checkEnableButton(): Boolean {
        with(mutableState.value.viewData) {
            val isButtonEnabled = projectNameField.isValid &&
                    packageNameField.isValid &&
                    templateUrlField.isValid

            mutableState.value = FormScreenState.FillingForm(
                data = copy(enableButton = isButtonEnabled)
            )

            return isButtonEnabled
        }
    }

    private fun loading(isLoading: Boolean) {
        mutableState.value = FormScreenState.FillingForm(
            mutableState.value.viewData.copy(
                isLoading = isLoading
            )
        )
    }

    private fun handleError(errorMessage: String): FormScreenState {
        println(errorMessage)
        return FormScreenState.Error(mutableState.value.viewData.copy(errorMessage = errorMessage))
    }

    private fun validateField(validation: (FormViewData) -> Unit) {
        if (mutableState.value is Initial) return
        validation(mutableState.value.viewData)
    }

    private fun successField(field: FormFieldViewData) = field.copy(
        errorMessage = null,
        isValid = true
    )

    private fun failureField(field: FormFieldViewData, errorMessage: String) = field.copy(
        errorMessage = errorMessage,
        isValid = false
    )

}