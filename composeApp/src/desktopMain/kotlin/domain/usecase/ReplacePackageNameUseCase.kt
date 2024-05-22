package domain.usecase

import domain.model.CloneRepositoryModel
import domain.repository.FileRepository

class ReplacePackageNameUseCase(
    private val fileRepository: FileRepository,
    private val replacePackageFolderNameUseCase: ReplacePackageFolderNameUseCase
) {

    operator fun invoke(cloneRepositoryModel: CloneRepositoryModel): Result<Unit> {
        return fileRepository.replacePackageName(cloneRepositoryModel).onSuccess {
            println("Package name replaced successfully!")
            replacePackageFolderNameUseCase(cloneRepositoryModel)
        }
    }
}