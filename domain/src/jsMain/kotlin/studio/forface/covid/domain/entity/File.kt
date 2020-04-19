package studio.forface.covid.domain.entity

import okio.BufferedSource

actual open class File {

    actual val name: String
        get() = TODO("Not yet implemented")

    actual fun bufferedSource(): BufferedSource {
        TODO("Not yet implemented")
    }

}

actual class Directory : File() {
    /** @return List of [File]s contained in this directory */
    actual fun files(): List<File> {
        TODO("Not yet implemented")
    }

    /** Write given [file] in this directory */
    actual fun createFile(file: File) {
    }

    /** Create a File writing the [BufferedSource] with the given [name] */
    actual fun createFile(source: BufferedSource, name: String) {
    }

    /** Delete all the files contained in this directory */
    actual fun deleteFiles() {
    }
}