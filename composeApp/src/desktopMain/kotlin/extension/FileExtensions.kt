package extension

import java.io.File

private const val PATH_SEPARATOR = "/"

fun File.addPath(path: List<String>) = File(this, path.joinToString(PATH_SEPARATOR))

fun File.addPath(path: String) = File(this, path)

fun File.findAndReplaceText(oldText: String, newText: String) {
    writeText(readText().replace(oldText, newText))
}

fun File.rename(newName: String) {
    renameTo(File(parentFile, newName))
}
