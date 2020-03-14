package studio.forface.covid.data.local

import kotlin.jvm.JvmName

/*
 * Typealias for database models, which have the same names as entities
 * Author: Davide Farella
 */

typealias CountryDbModel = Country
typealias CountryWithProvinceDbModel = CountryWithProvinces
typealias CountryDbModelImpl = Country.Impl
typealias CountryWithProvinceDbModelImpl = CountryWithProvinces.Impl

typealias ProvinceDbModel = Province
typealias ProvinceDbModelImpl = Province.Impl

internal sealed class ProvinceWrapper {
    data class Standalone(val province: ProvinceDbModel) : ProvinceWrapper()
    data class FromCounty(val countryWithProvince: CountryWithProvinceDbModel) : ProvinceWrapper()
}

internal val ProvinceDbModel.wrap get() = ProvinceWrapper.Standalone(this)
@get:JvmName("List_ProvinceDbModel_wrap")
internal val List<ProvinceDbModel>.wrap get() = map { it.wrap }
internal val CountryWithProvinceDbModel.wrap get() = ProvinceWrapper.FromCounty(this)
@get:JvmName("List_CountyWithProvinceDbModel_wrap")
internal val List<CountryWithProvinceDbModel>.wrap get() = map { it.wrap }

typealias StatDbModel = Stat
typealias StatDbModelImpl = Stat.Impl

