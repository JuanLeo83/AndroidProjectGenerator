package presentation.screen.form

import domain.error.EmptyFieldException
import domain.error.InvalidPackageFormatException
import domain.error.InvalidTemplateUrlException
import domain.model.CloneRepositoryModel
import strings.Texts

class FormMapper {

    fun mapToCloneRepositoryModel(viewData: FormViewData): CloneRepositoryModel {
        return CloneRepositoryModel(
            repositoryUrl = viewData.templateUrlField.value,
            projectName = viewData.projectNameField.value,
            destinationPath = viewData.destinationPathField.value,
            packageName = viewData.packageNameField.value,
            oldPackageName = viewData.oldPackageNameField.value
        )
    }

    fun mapProjectNameErrorMessage(e: Throwable): String {
        return when (e) {
            is EmptyFieldException -> Texts.res.projectNameEmptyError
            else -> Texts.res.unknownError
        }
    }

    fun mapPackageNameErrorMessage(e: Throwable): String {
        return when (e) {
            is EmptyFieldException -> Texts.res.packageNameEmptyError
            is InvalidPackageFormatException -> Texts.res.packageNameInvalidFormatError
            else -> Texts.res.unknownError
        }
    }

    fun mapTemplateUrlErrorMessage(e: Throwable): String {
        return when (e) {
            is EmptyFieldException -> Texts.res.templateUrlEmptyError
            is InvalidTemplateUrlException -> Texts.res.templateUrlInvalidFormatError
            else -> Texts.res.unknownError
        }
    }
}