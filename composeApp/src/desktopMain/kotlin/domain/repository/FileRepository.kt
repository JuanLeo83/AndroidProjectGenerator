package domain.repository

import domain.model.CloneRepositoryModel

interface FileRepository {
    fun deleteGitFolder(path: String): Result<Unit>
    fun replacePackageName(cloneRepositoryModel: CloneRepositoryModel): Result<Unit>
    fun replacePackageFolderName(cloneRepositoryModel: CloneRepositoryModel): Result<Unit>
    fun replaceApplicationFileName(cloneRepositoryModel: CloneRepositoryModel): Result<Unit>
}