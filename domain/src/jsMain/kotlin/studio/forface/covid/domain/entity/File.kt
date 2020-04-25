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
    actual fun saveFile(file: File) {
        TODO("not implemented")
    }

    /** Create a File writing the [BufferedSource] with the given [name] */
    actual fun saveFile(name: String, data: ByteArray) {
        TODO("not implemented")
    }

    /** Delete all the files contained in this directory */
    actual fun deleteFiles() {
        TODO("not implemented")
    }
}
