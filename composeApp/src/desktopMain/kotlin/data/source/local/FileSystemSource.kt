package data.source.local

import domain.model.CloneRepositoryModel
import extension.EMPTY_STRING
import extension.addPath
import extension.findAndReplaceText
import extension.rename
import java.io.File

class FileSystemSource {

    fun deleteGitFolder(path: String): Result<Unit> = runCatching {
        File("$path/$GIT_FOLDER").apply {
            if (!exists()) return Result.success(Unit)

            if (isDirectory) {
                deleteRecursively()
            } else {
                delete()
            }
        }
    }

    fun replacePackageName(cloneRepositoryModel: CloneRepositoryModel): Result<Unit> = runCatching {
        File(cloneRepositoryModel.projectPath).apply {
            if (!exists()) return Result.failure(
                Exception("File not found: $cloneRepositoryModel.projectPath")
            )

            val files = listFiles() ?: return Result.success(Unit)

            files.forEach { file ->
                if (file.isDirectory) {
                    replacePackageName(
                        cloneRepositoryModel.copy(
                            destinationPath = file.absolutePath,
                            projectName = String.EMPTY_STRING
                        )
                    )
                } else {
                    file.findAndReplaceText(
                        cloneRepositoryModel.oldPackageName,
                        cloneRepositoryModel.packageName
                    )
                }
            }
        }
    }

    fun replacePackageFolderName(cloneRepositoryModel: CloneRepositoryModel): Result<Unit> =
        runCatching {
            val newPackageName = cloneRepositoryModel.packageName.split(PACKAGE_SEPARATOR)
            val oldPackageName = cloneRepositoryModel.oldPackageName.split(PACKAGE_SEPARATOR)

            checkAndMoveToNewPackage(
                "${cloneRepositoryModel.projectPath}$JAVA_PATH",
                newPackageName,
                oldPackageName
            )

            checkAndMoveToNewPackage(
                "${cloneRepositoryModel.projectPath}$ANDROID_TEST_PATH",
                newPackageName,
                oldPackageName
            )

            checkAndMoveToNewPackage(
                "${cloneRepositoryModel.projectPath}$TEST_PATH",
                newPackageName,
                oldPackageName
            )
        }

    fun replaceApplicationFileName(cloneRepositoryModel: CloneRepositoryModel): Result<Unit> =
        runCatching {
            with(cloneRepositoryModel) {
                val newPackageName = packageName.split(PACKAGE_SEPARATOR)
                val newName = projectName.replaceFirstChar { c -> c.uppercaseChar() }
                val newFileName = "${newName}$APPLICATION"

                File("${projectPath}$JAVA_PATH")
                    .addPath(newPackageName)
                    .addPath(APPLICATION_FILE_NAME + KOTLIN_EXTENSION)
                    .run {
                        if (exists()) {
                            findAndReplaceText(APPLICATION_FILE_NAME, newFileName)
                            rename("${newFileName}$KOTLIN_EXTENSION")
                        }
                    }

                File("${projectPath}$MAIN_PATH$MANIFEST_FILE").run {
                    if (exists()) {
                        findAndReplaceText(APPLICATION_FILE_NAME, newName)
                    }
                }

                File("${projectPath}$THEMES_PATH").run {
                    if (exists()) {
                        findAndReplaceText(APPLICATION_FILE_NAME, newName)
                    }
                }

                File("${projectPath}$STRINGS_PATH").run {
                    if (exists()) {
                        findAndReplaceText(APPLICATION_FILE_NAME, newName)
                    }
                }
            }
        }

    private fun checkAndMoveToNewPackage(
        path: String,
        newPackage: List<String>,
        oldPackage: List<String>
    ) {
        File(path).run {
            if (exists()) {
                moveToNewPackage(newPackage, oldPackage, this)
            }
        }
    }

    private fun moveToNewPackage(
        newPackageNameComponents: List<String>,
        oldPackageNameComponents: List<String>,
        currentDirectory: File
    ): Result<Unit> = runCatching {
        val newPackage = currentDirectory.addPath(newPackageNameComponents).apply {
            mkdirs()
        }

        currentDirectory.addPath(oldPackageNameComponents).run {
            copyRecursively(newPackage)
            currentDirectory.addPath(oldPackageNameComponents.first()).run {
                if (exists()) {
                    deleteRecursively()
                }
            }
        }
    }

    companion object {
        private const val PACKAGE_SEPARATOR = "."
        private const val GIT_FOLDER = ".git"
        private const val MAIN_PATH = "/app/src/main/"
        private const val JAVA_PATH = "${MAIN_PATH}java"
        private const val THEMES_PATH = "${MAIN_PATH}res/values/themes.xml"
        private const val STRINGS_PATH = "${MAIN_PATH}res/values/strings.xml"
        private const val ANDROID_TEST_PATH = "/app/src/androidTest/java"
        private const val TEST_PATH = "/app/src/test/java"
        private const val APPLICATION_FILE_NAME = "TemplateApp"
        private const val KOTLIN_EXTENSION = ".kt"
        private const val MANIFEST_FILE = "AndroidManifest.xml"
        private const val APPLICATION = "App"
    }
}