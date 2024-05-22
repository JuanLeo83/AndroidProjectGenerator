package data.repository

import data.source.local.GitSource
import domain.model.CloneRepositoryModel
import domain.repository.GitRepository

class GitRepositoryImpl(private val source: GitSource) : GitRepository {
    override fun clone(cloneRepositoryModel: CloneRepositoryModel): Result<Unit> {
        return source.cloneGitRepository(cloneRepositoryModel)
    }
}