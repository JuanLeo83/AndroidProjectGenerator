package domain.usecase

import domain.error.EmptyFieldException
import domain.error.InvalidTemplateUrlException

class ValidateTemplateUrlUseCase {

    operator fun invoke(templateUrl: String): Result<Unit> {
        return if (templateUrl.isEmpty()) {
            Result.failure(EmptyFieldException())
        } else if (!templateUrl.matches(GITHUB_REGEX)) {
            Result.failure(InvalidTemplateUrlException())
        } else Result.success(Unit)
    }

    companion object {
        private val GITHUB_REGEX = Regex("^https://github\\.com/[a-zA-Z0-9_.-]+/[a-zA-Z0-9_.-]+\\.git$")
    }
}