package studio.forface.covid.domain.entity

import com.soywiz.klock.DateTime
import okio.BufferedSource

interface Version {
    val code: Int
    val name: String

    companion object {
        val Empty: Version = BaseVersion(0, "")
    }
}

operator fun Version.compareTo(other: Version) = code.compareTo(other.code)
operator fun Version.compareTo(other: BaseVersion) = code.compareTo(other.code)
operator fun Version.compareTo(other: UpdateVersion) = code.compareTo(other.code)
operator fun Version.compareTo(other: InstallableUpdateVersion) = code.compareTo(other.code)

data class BaseVersion(
    override val code: Int,
    override val name: String
) : Version

operator fun BaseVersion.compareTo(other: Version) = code.compareTo(other.code)

data class UpdateVersion(
    override val code: Int,
    override val name: String,
    val timestamp: DateTime
) : Version

operator fun UpdateVersion.compareTo(other: Version) = code.compareTo(other.code)

data class InstallableUpdateVersion(
    val file: BufferedSource,
    val updateVersion: UpdateVersion
) : Version by updateVersion

operator fun InstallableUpdateVersion.compareTo(other: Version) = updateVersion.code.compareTo(other.code)

