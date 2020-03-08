package studio.forface.covid.data.remote.mapper

import com.soywiz.klock.DateTime
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
    override fun String.toEntity() = DateTime.parse(this).local
}

internal class UnixTimeApiModelMapper : ApiModelMapper<Int, DateTime> {
    override fun Int.toEntity() = DateTime(this.toLong())
}
