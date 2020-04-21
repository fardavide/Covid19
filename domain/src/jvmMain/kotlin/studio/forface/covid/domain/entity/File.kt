package studio.forface.covid.domain.entity

import okio.buffer
import okio.source
import java.io.IOException

actual open class File(val delegate: java.io.File) {

    actual val name: String get() = delegate.name

    actual fun bufferedSource() = delegate.source().buffer()
}

actual class Directory(delegate: java.io.File) : File(delegate) {

    actual fun files(): List<File> = delegate.listFiles().orEmpty().map { File(it) }

    actual fun saveFile(file: File) {
        file.delegate.copyTo(java.io.File(delegate, file.name))
    }

    actual fun saveFile(name: String, data: ByteArray) {
        val dest = java.io.File(delegate, name).also { it.createNewFile() }
        dest.writeBytes(data)
    }

    actual fun deleteFiles() {
        delegate.listFiles()?.forEach {
            if (!it.delete()) throw IOException("Cannot delete file: '${it.path}'")
        }
    }
}
