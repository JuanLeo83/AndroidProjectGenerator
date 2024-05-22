package domain.usecase

import domain.error.EmptyFieldException
import domain.error.InvalidPackageFormatException

class ValidatePackageNameUseCase {

    operator fun invoke(corePackage: String): Result<Unit> {
        return if (corePackage.isEmpty()) {
            Result.failure(EmptyFieldException())
        } else if (corePackage.matches(REGEX).not()) {
            Result.failure(InvalidPackageFormatException())
        } else Result.success(Unit)
    }

    companion object {
        private val REGEX = Regex("^[a-z]+(\\.[a-z]+)+$")
    }
}