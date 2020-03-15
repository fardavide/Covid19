package studio.forface.covid.data.local.model

import studio.forface.covid.data.local.CountryWithProvinces
import studio.forface.covid.data.local.Province
import studio.forface.covid.data.local.ProvinceStats
import kotlin.jvm.JvmName

internal typealias ProvinceDbModel = Province
internal typealias ProvinceDbModelImpl = Province.Impl
internal typealias CountryWithProvinceDbModel = CountryWithProvinces
internal typealias CountryWithProvinceDbModelImpl = CountryWithProvinces.Impl
internal typealias ProvinceStatPlainDbModel = ProvinceStats
internal typealias ProvinceStatPlainDbModelImpl = ProvinceStats.Impl

internal sealed class ProvinceWrapper {
    /** It contains only the Province model and it's used for create a Province entity */
    data class Standalone(val province: ProvinceDbModel) : ProvinceWrapper()
    /**
     * It contains the Province model and relative Country. It's used for create a County, which requires a list of
     * Provinces
     */
    data class FromCounty(val countryWithProvince: CountryWithProvinceDbModel) : ProvinceWrapper()
}

// region Utils for crate `ProvinceWrapper` or a list of them
internal val ProvinceDbModel.wrap get() = ProvinceWrapper.Standalone(this)
@get:JvmName("List_ProvinceDbModel_wrap")
internal val List<ProvinceDbModel>.wrap get() = map { it.wrap }
internal val CountryWithProvinceDbModel.wrap get() = ProvinceWrapper.FromCounty(this)
@get:JvmName("List_CountyWithProvinceDbModel_wrap")
internal val List<CountryWithProvinceDbModel>.wrap get() = map { it.wrap }
// endregion
