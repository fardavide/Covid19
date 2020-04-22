package studio.forface.covid.domain

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import studio.forface.covid.domain.entity.BaseVersion
import studio.forface.covid.domain.entity.InstallableUpdateVersion
import studio.forface.covid.domain.entity.UpdateVersion
import studio.forface.covid.domain.gateway.Api
import studio.forface.covid.domain.gateway.Repository
import studio.forface.covid.domain.gateway.UpdatesApi
import studio.forface.covid.domain.gateway.UpdatesRepository
import studio.forface.covid.domain.usecase.updates.BuildDownloadableUpdateFileName
import studio.forface.covid.domain.usecase.updates.GetAppVersion
import studio.forface.covid.domain.usecase.updates.GetInstallableUpdate

fun MockApi() = mockk<Api>()

fun MockUpdatesApi(
    lastUpdateVersion: UpdateVersion
) = mockk<UpdatesApi> {
    coEvery { getLastUpdateVersion() } returns lastUpdateVersion
    coEvery { getUpdateVersion(any()) } returns mockk()
    coEvery { getUpdateFile(any()) } returns ByteArray(8)
}

fun MockRepository() = mockk<Repository>()

fun MockUpdatesRepository() = mockk<UpdatesRepository>(relaxed = true)

fun MockBuildDownloadableUpdateFileName() = mockk<BuildDownloadableUpdateFileName> {
    val m = this
    coEvery { m(any<UpdateVersion>()) } coAnswers { m(firstArg<UpdateVersion>().name) }
    coEvery { m(any<String>()) } coAnswers { "Covid${firstArg<String>()}" }
}

fun MockGetAppVersion(version: BaseVersion) = mockk<GetAppVersion> {
    val m = this
    every { m() } returns version
}

fun MockGetInstallableUpdate(
    version: InstallableUpdateVersion
) = mockk<GetInstallableUpdate> {
    val m = this
    coEvery { m() } returns version
}
