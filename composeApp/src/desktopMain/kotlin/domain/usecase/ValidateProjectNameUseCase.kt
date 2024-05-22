package domain.usecase

import domain.error.EmptyFieldException

class ValidateProjectNameUseCase {

    operator fun invoke(projectName: String): Result<Unit> {
        return if (projectName.isEmpty()) {
            Result.failure(EmptyFieldException())
        } else Result.success(Unit)
    }
}