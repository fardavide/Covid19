package studio.forface.covid.data.local.mapper

import com.soywiz.klock.DateTime

internal class UnitTimeDbModelMapper : DatabaseModelMapper<Double, DateTime> {

    override fun Double.toEntity() = DateTime(this)
    override fun DateTime.toDatabaseModel() = unixMillis
}
