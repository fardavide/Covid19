package studio.forface.covid.data.local.mapper

import com.soywiz.klock.DateTime
import studio.forface.covid.domain.entity.Location
import studio.forface.covid.domain.mapper.TwoWayMapper

internal class LocationDbModelMapper : DatabaseModelMapper<Pair<Double, Double>, Location> {
    override fun Pair<Double, Double>.toEntity() = Location(first, second)
}

internal class UnixTimeDbModelMapper : TwoWayMapper<Double, DateTime, Double> {
    fun Double.toEntity() = DateTime(this)
    fun DateTime.toModel() = unixMillisDouble
}
