package studio.forface.covid.domain.entity

import okio.BufferedSource
import okio.buffer
import okio.sink
import okio.source
import java.io.IOException

actual open class File(path: String) {
    internal val delegate = java.io.File(path)

    actual val name: String get() = delegate.name

    actual fun bufferedSource() = delegate.source().buffer()
}

actual class Directory(path: String) : File(path) {

    actual fun files(): List<File> = delegate.listFiles().orEmpty().map { File(it.path) }

    actual fun createFile(file: File) {
        file.delegate.copyTo(java.io.File(delegate, file.name))
    }

    actual fun createFile(source: BufferedSource, name: String) {
        java.io.File(delegate, name).sink().buffer().writeAll(source)
    }

    actual fun deleteFiles() {
        delegate.listFiles()?.forEach {
            if (!it.delete()) throw IOException("Cannot delete file: '${it.path}'")
        }
    }
}
