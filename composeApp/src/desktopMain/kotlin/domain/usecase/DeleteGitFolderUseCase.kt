package domain.usecase

import domain.model.CloneRepositoryModel
import domain.repository.FileRepository

class DeleteGitFolderUseCase(
    private val fileRepository: FileRepository,
    private val replacePackageNameUseCase: ReplacePackageNameUseCase
) {
    operator fun invoke(cloneRepositoryModel: CloneRepositoryModel): Result<Unit> {
        return fileRepository.deleteGitFolder(cloneRepositoryModel.projectPath).onSuccess {
            println("Git folder deleted successfully!")
            replacePackageNameUseCase(cloneRepositoryModel)
        }
    }
}