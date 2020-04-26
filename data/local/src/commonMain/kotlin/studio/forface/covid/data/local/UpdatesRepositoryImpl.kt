package studio.forface.covid.data.local

import studio.forface.covid.domain.entity.Directory
import studio.forface.covid.domain.gateway.UpdatesRepository

/**
 * Default Implementation of [UpdatesRepository]
 * @author Davide Farella
 */
internal class UpdatesRepositoryImpl(
    private val updatesDirectory: Directory
) : UpdatesRepository {

    override suspend fun getUpdate() = updatesDirectory.files().firstOrNull()

    override suspend fun storeUpdate(data: ByteArray, name: String) {
        updatesDirectory.apply {
            createIfNoExists()
            deleteFiles()
            saveFile(name, data)
        }
    }
}
