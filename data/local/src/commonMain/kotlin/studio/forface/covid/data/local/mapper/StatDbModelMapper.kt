package studio.forface.covid.data.local.mapper

import studio.forface.covid.data.local.model.CountryStatPlainDbModel
import studio.forface.covid.data.local.model.CountryWithProvincesStatPlainDbModel
import studio.forface.covid.data.local.model.ProvinceStatPlainDbModel
import studio.forface.covid.data.local.model.StatDbModel
import studio.forface.covid.domain.entity.Stat
import studio.forface.covid.domain.invoke

// TODO: remove
internal class StatDbModelMapper(
    private val timeMapper: UnixTimeDbModelMapper
) : DatabaseModelMapper<StatDbModel, Stat> {

    override fun StatDbModel.toEntity() = Stat(
        confirmed = confirmed,
        deaths = deaths,
        recovered = recovered,
        timestamp = timeMapper { timestamp.toEntity() }
    )
}

// region Plain mappers
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

// TODO: remove
internal class ProvinceStatFromCountryWithProvincesStatPlainDbModelMapper(
    private val timeMapper: UnixTimeDbModelMapper
) : DatabaseModelMapper<CountryWithProvincesStatPlainDbModel, Stat> {

    override fun CountryWithProvincesStatPlainDbModel.toEntity() = Stat(
        confirmed = provinceConfirmed,
        deaths = provinceDeaths,
        recovered = provinceRecovered,
        timestamp = timeMapper { provinceTimestamp.toEntity() }
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
// endregion
