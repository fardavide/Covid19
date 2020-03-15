package studio.forface.covid.data.remote.mapper

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import com.soywiz.klock.parse
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.entity.Id
import studio.forface.covid.domain.entity.Location
import studio.forface.covid.domain.entity.Name
import studio.forface.covid.domain.entity.ProvinceId
import studio.forface.covid.domain.entity.WorldId

internal class WorldIdApiModelMapper : ApiModelMapper<String, Id> {
    override fun String.toEntity() = WorldId(this)
}

internal class CountryIdApiModelMapper : ApiModelMapper<String, Id> {
    override fun String.toEntity() = CountryId(this)
}

internal class ProvinceIdApiModelMapper : ApiModelMapper<String, Id> {
    override fun String.toEntity() = ProvinceId(this)
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
