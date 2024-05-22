package data.repository

import data.source.local.FileSystemSource
import domain.model.CloneRepositoryModel
import domain.repository.FileRepository

class FileRepositoryImpl(private val source: FileSystemSource) : FileRepository {
    override fun deleteGitFolder(path: String): Result<Unit> {
        return source.deleteGitFolder(path)
    }

    override fun replacePackageName(cloneRepositoryModel: CloneRepositoryModel): Result<Unit> {
        return source.replacePackageName(cloneRepositoryModel)
    }

    override fun replacePackageFolderName(cloneRepositoryModel: CloneRepositoryModel): Result<Unit> {
        return source.replacePackageFolderName(cloneRepositoryModel)
    }

    override fun replaceApplicationFileName(cloneRepositoryModel: CloneRepositoryModel): Result<Unit> {
        return source.replaceApplicationFileName(cloneRepositoryModel)
    }
}