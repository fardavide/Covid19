package studio.forface.covid.data.remote.mapper

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import com.soywiz.klock.parse
import studio.forface.covid.domain.entity.Id
import studio.forface.covid.domain.entity.Location
import studio.forface.covid.domain.entity.Name

internal class IdApiModelMapper : ApiModelMapper<String, Id> {
    override fun String.toEntity() = Id(this)
}

internal class NameApiModelMapper : ApiModelMapper<String, Name> {
    override fun String.toEntity() = Name(this)
}

internal class LocationApiModelMapper : ApiModelMapper<LocationParams, Location> {
    override fun LocationParams.toEntity() = Location(lat, lng)
}

data class LocationParams(val lat: Double, val lng: Double)

internal class TimestampApiModelMapper : ApiModelMapper<String, DateTime> {
    override fun String.toEntity() = DATE_FORMAT.parse(this).local
    private companion object { val DATE_FORMAT = DateFormat("yyyy-MM-dd'T'HH:mm:ssZ") }
}

internal class UnixTimeApiModelMapper : ApiModelMapper<Int, DateTime> {
    override fun Int.toEntity() = DateTime(this * A_SEC_IN_MS)
    private companion object { const val A_SEC_IN_MS = 1000L }
}
