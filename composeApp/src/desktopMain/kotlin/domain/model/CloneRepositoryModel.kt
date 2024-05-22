package domain.model

data class CloneRepositoryModel(
    val repositoryUrl: String,
    val destinationPath: String,
    val projectName: String,
    val packageName: String,
    val oldPackageName: String
) {
    val projectPath: String = "${destinationPath}/${projectName}"
}
