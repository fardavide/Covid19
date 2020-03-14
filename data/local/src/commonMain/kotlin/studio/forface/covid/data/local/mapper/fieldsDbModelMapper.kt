package studio.forface.covid.data.local.mapper

import com.soywiz.klock.DateTime
import studio.forface.covid.domain.entity.Location

internal class LocationDbModelMapper : DatabaseModelMapper<Pair<Double, Double>, Location> {
    override fun Pair<Double, Double>.toEntity() = Location(first, second)
}

internal class UnixTimeDbModelMapper : DatabaseModelMapper<Double, DateTime> {
    override fun Double.toEntity() = DateTime(this)
}
