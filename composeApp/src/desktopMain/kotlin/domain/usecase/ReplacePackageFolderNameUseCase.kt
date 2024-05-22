package domain.usecase

import domain.model.CloneRepositoryModel
import domain.repository.FileRepository

class ReplacePackageFolderNameUseCase(
    private val repository: FileRepository,
    private val replaceApplicationFileNameUseCase: ReplaceApplicationFileNameUseCase
) {

    operator fun invoke(cloneRepositoryModel: CloneRepositoryModel): Result<Unit> {
        return repository.replacePackageFolderName(cloneRepositoryModel).onSuccess {
            println("Package folder name replaced successfully!")
            replaceApplicationFileNameUseCase(cloneRepositoryModel)
        }
    }
}