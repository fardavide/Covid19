package studio.forface.covid.domain.entity

import okio.BufferedSource

/**
 * Representation of a File on FileSystem
 * @author Davide Farella
 */
expect open class File {

    val name: String

    fun bufferedSource(): BufferedSource
}

expect class Directory : File {

    /** @return List of [File]s contained in this directory */
    fun files(): List<File>

    /** Write given [file] in this directory */
    fun saveFile(file: File)

    /** Create a File writing the [BufferedSource] with the given [name] */
    fun saveFile(name: String, data: ByteArray)

    /** Delete all the files contained in this directory */
    fun deleteFiles()
}
