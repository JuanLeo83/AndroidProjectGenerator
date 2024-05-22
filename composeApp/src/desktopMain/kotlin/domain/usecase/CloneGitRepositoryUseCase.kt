package domain.usecase

import domain.model.CloneRepositoryModel
import domain.repository.GitRepository

class CloneGitRepositoryUseCase(
    private val repository: GitRepository,
    private val deleteGitFolderUseCase: DeleteGitFolderUseCase
) {

    operator fun invoke(cloneRepositoryModel: CloneRepositoryModel): Result<Unit> {
        return repository.clone(cloneRepositoryModel).onSuccess {
            println("Repository cloned successfully!")
            deleteGitFolderUseCase(cloneRepositoryModel)
        }
    }
}