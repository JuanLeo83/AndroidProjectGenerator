package data.source.local

import domain.model.CloneRepositoryModel
import java.io.File
import java.lang.ProcessBuilder.Redirect.INHERIT
import java.util.concurrent.TimeUnit

class GitSource {
    fun cloneGitRepository(cloneRepositoryModel: CloneRepositoryModel): Result<Unit> = runCatching {
        with(cloneRepositoryModel) {
            val workingDir = File(destinationPath)
            val gitCommand = "$GIT_CLONE_COMMAND $repositoryUrl $projectName".trimEnd()

            println(gitCommand)

            ProcessBuilder(*gitCommand.split(SEPARATOR).toTypedArray())
                .directory(workingDir)
                .redirectOutput(INHERIT)
                .redirectError(INHERIT)
                .start()
                .waitFor(TIMEOUT_MINUTES, TimeUnit.MINUTES)
        }

        Result.success(Unit)
    }

    companion object {
        private const val TIMEOUT_MINUTES: Long = 1
        private const val GIT_CLONE_COMMAND = "git clone"
        private const val SEPARATOR = " "
    }
}