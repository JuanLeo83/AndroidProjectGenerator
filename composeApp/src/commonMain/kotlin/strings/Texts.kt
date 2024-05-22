package strings

object Texts {
    var res: LocalizationModel = en
}

data class LocalizationModel(
    val projectNameLabel: String = "",
    val projectNameEmptyError: String = "",

    val packageNameLabel: String = "",
    val oldPackageNameLabel: String = "",
    val packageNameEmptyError: String = "",
    val packageNameInvalidFormatError: String = "",

    val destinationPathLabel: String = "",

    val templateUrlLabel: String = "",
    val templateUrlEmptyError: String = "",
    val templateUrlInvalidFormatError: String = "",

    val unknownError: String = "",

    val generateProjectButton: String = "",

    val english: String = "EN",
    val spanish: String = "ES"
)