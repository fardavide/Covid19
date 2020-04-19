import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.dokka.gradle.DokkaTask
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * This class will organize release.
 *
 * It will:
 * * Move aar archives into '/folderName/<libName>'
 * * Move apk archives into '/folderName/apk'
 * * Generate KDoc if new version is available
 *
 * @author Davide Farella
 */
class ReleaseManager internal constructor(project: Project) : Project by project {

    private var shouldGenerateKdoc = false
    private var newVersion: String? = null

    /** Move aar archives into '/[folderName]/<libName>' */
    fun moveArchives(folderName: String) {
        move(folderName, archiveType = ArchiveType.AAR)
    }

    /** Move apk's into '/[folderName]/apk' */
    fun moveApk(folderName: String) {
        move(folderName, archiveType = ArchiveType.APK)
    }

    private fun move(folderName: String, archiveType: ArchiveType) {
        // Setup folder
        val newDir = File(rootDir, folderName + File.separator + fullName)
        if (!newDir.exists()) newDir.mkdirs()

        when (archiveType) {
            ArchiveType.APK -> {
                APK_DIRECTORY.listFiles { file, _ -> file.isDirectory }?.forEach { subDir ->
                    val newSubDir = File(newDir, subDir.name)
                        .also { if (!it.exists()) it.mkdir() }
                    subDir.listFiles()?.filter { it.extension == "apk" }?.forEach { apk ->
                        val newFile = File(newSubDir, apk.name)
                        // If file is absent, copy it and generate KDoc
                        if (!newFile.exists()) {
                            apk.copyTo(newFile)
                            shouldGenerateKdoc = true
                            newVersion = apk.name
                                .substringAfterLast('_')
                                .substringBeforeLast("-${subDir.name}")
                        }
                        apk.delete()
                    }
                }
            }
            ArchiveType.AAR -> {
                AAR_DIRECTORY.listFiles { _, path -> "release" in path }?.forEach {
                    val newFile = File(newDir, it.name.replace("-release", ""))
                    // If file is absent, copy it and generate KDoc
                    if (!newFile.exists()) {
                        it.copyTo(newFile)
                        shouldGenerateKdoc = true
                        newVersion = it.name
                            .substringAfterLast('_')
                            .substringBeforeLast('-')
                    }
                    it.delete()
                }
            }
        }
    }

    /** Generate KDoc if new library is available */
    fun generateKdocIfNeeded() {
        if (shouldGenerateKdoc) tasks.getByName<DokkaTask>("dokka").generate()
    }

    /** Update readme with new version */
    fun updateReadme() {
        if (newVersion != null) {
            val timestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))

            README_FILE.writeText(
                README_FILE.readText()
                    .replace(README_VERSION_REGEX, readmeVersion(name, newVersion!!, timestamp))
            )
        }
    }

    private enum class ArchiveType { APK, AAR }

    private companion object {
        val Project.AAR_DIRECTORY get() = File(buildDir, "outputs" + File.separator + "aar")
        val Project.APK_DIRECTORY get() = File(buildDir, "outputs" + File.separator + "apk")
        val Project.README_FILE get() = File(rootDir, "README.md")
        val Project.README_VERSION_REGEX get() = readmeVersion(name, "(.+)", "(.+)").toRegex()

        @OptIn(ExperimentalStdlibApi::class) // String.capitalize(Locale)
        fun readmeVersion(name: String, version: String, timestamp: String) =
            """${name.capitalize(Locale.getDefault())}: \*\*$version\*\* - _released on: ${timestamp}_"""
    }
}

val Project.fullName get() = with(StringBuilder(name)) {
    var p = parent
    while (p != null && p != rootProject) {
        insert(0, "${p.name}-")
        p = p.parent
    }
    toString()
}

val Project.ReleaseManager get() = ReleaseManager(this)

