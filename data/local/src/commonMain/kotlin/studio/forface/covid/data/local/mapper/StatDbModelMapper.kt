package studio.forface.covid.data.local.mapper

import studio.forface.covid.data.local.model.CountryStatPlainDbModel
import studio.forface.covid.data.local.model.CountryWithProvincesStatPlainDbModel
import studio.forface.covid.data.local.model.ProvinceStatPlainDbModel
import studio.forface.covid.data.local.model.WorldStatPlainDbModel
import studio.forface.covid.data.local.model.WorldWithProvinceStatPlainDbModel
import studio.forface.covid.domain.entity.Stat
import studio.forface.covid.domain.invoke

internal class WorldStatFromWorldWithProvincesStatPlainDModelMapper(
    private val timeMapper: UnixTimeDbModelMapper
) : DatabaseModelMapper<WorldWithProvinceStatPlainDbModel, Stat> {

    override fun WorldWithProvinceStatPlainDbModel.toEntity() = Stat(
        confirmed = worldConfirmed,
        deaths = worldDeaths,
        recovered = worldRecovered,
        timestamp = timeMapper { worldTimestamp.toEntity() }
    )
}

internal class WorldStatPlainDModelMapper(
    private val timeMapper: UnixTimeDbModelMapper
) : DatabaseModelMapper<WorldStatPlainDbModel, Stat> {

    override fun WorldStatPlainDbModel.toEntity() = Stat(
        confirmed = worldConfirmed,
        deaths = worldDeaths,
        recovered = worldRecovered,
        timestamp = timeMapper { worldTimestamp.toEntity() }
    )
}

internal class CountryStatPlainDbModelMapper(
    private val timeMapper: UnixTimeDbModelMapper
) : DatabaseModelMapper<CountryStatPlainDbModel, Stat> {

    override fun CountryStatPlainDbModel.toEntity() = Stat(
        confirmed = confirmed,
        deaths = deaths,
        recovered = recovered,
        timestamp = timeMapper { timestamp.toEntity() }
    )
}

internal class CountryStatFromCountryWithProvinceStatPlainDbModelMapper(
    private val timeMapper: UnixTimeDbModelMapper
) : DatabaseModelMapper<CountryWithProvincesStatPlainDbModel, Stat> {

    override fun CountryWithProvincesStatPlainDbModel.toEntity() = Stat(
        confirmed = countryConfirmed,
        deaths = countryDeaths,
        recovered = countryRecovered,
        timestamp = timeMapper { countryTimestamp.toEntity() }
    )
}

internal class ProvinceStatPlainDbModelMapper(
    private val timeMapper: UnixTimeDbModelMapper
) : DatabaseModelMapper<ProvinceStatPlainDbModel, Stat> {

    override fun ProvinceStatPlainDbModel.toEntity() = Stat(
        confirmed = confirmed,
        deaths = deaths,
        recovered = recovered,
        timestamp = timeMapper { timestamp.toEntity() }
    )
}
