package domain.repository

import domain.model.CloneRepositoryModel

interface GitRepository {
    fun clone(cloneRepositoryModel: CloneRepositoryModel): Result<Unit>
}