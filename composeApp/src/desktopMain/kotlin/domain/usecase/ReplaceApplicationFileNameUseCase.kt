package domain.usecase

import domain.model.CloneRepositoryModel
import domain.repository.FileRepository

class ReplaceApplicationFileNameUseCase(
    private val fileRepository: FileRepository
) {
    operator fun invoke(cloneRepositoryModel: CloneRepositoryModel): Result<Unit> {
        return fileRepository.replaceApplicationFileName(cloneRepositoryModel).onSuccess {
            println("Application file name replaced successfully!")
            println("Process finished!")
        }
    }
}