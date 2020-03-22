package studio.forface.covid.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import studio.forface.covid.data.remote.CovidService.DataType.Full
import studio.forface.covid.data.remote.CovidService.DataType.Small
import studio.forface.covid.data.remote.model.CountryFullStatApiModel
import studio.forface.covid.data.remote.model.CountrySmallStatApiModel
import studio.forface.covid.data.remote.model.CountryStatApiModel
import studio.forface.covid.data.remote.model.ProvinceFullStatApiModel
import studio.forface.covid.data.remote.model.ProvinceStatApiModel
import studio.forface.covid.data.remote.model.WorldFullStatApiModel
import studio.forface.covid.data.remote.model.WorldStatApiModel
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.entity.ProvinceId

internal class CovidService(
    private val client: HttpClient,
    private val host: String
) {

    suspend fun getCountries(): List<CountryStatApiModel> = getWorld<WorldFullStatApiModel>(Full).countryStats
    suspend fun getCountry(id: CountryId): CountryStatApiModel = getCountry(id, Small)
    suspend fun getProvinces(id: CountryId) = getCountry<CountryStatApiModel>(id, Small).provinceStats

    suspend fun getWorldStat(): WorldStatApiModel = getWorld(Small)
    suspend fun getWorldFullStat(): WorldFullStatApiModel = getWorld(Full)

    suspend fun getCountrySmallStat(id: CountryId): CountrySmallStatApiModel = getCountry(id, Small)
    suspend fun getCountryStat(id: CountryId): CountryStatApiModel = getCountry(id, Small)
    suspend fun getCountryFullStat(id: CountryId): CountryFullStatApiModel = getCountry(id, Full)

    suspend fun getProvinceStat(countryId: CountryId, id: ProvinceId): ProvinceStatApiModel =
        getProvince(countryId, id, Small)

    suspend fun getProvinceFullStat(countryId: CountryId, id: ProvinceId): ProvinceFullStatApiModel =
        getProvince(countryId, id, Full)


    private suspend inline fun <reified T> getWorld(
        dataType: DataType
    ): T = client.get("world", dataType)

    private suspend inline fun <reified T> getCountry(
        id: CountryId,
        dataType: DataType
    ): T = client.get("world/${id.s}", dataType)

    private suspend inline fun <reified T> getProvince(
        countryId: CountryId,
        id: ProvinceId,
        dataType: DataType
    ): T = client.get("world/${countryId.s}/${id.s}", dataType)

    private suspend inline fun <reified T> HttpClient.get(endpoint: String, dataType: DataType): T = client.get(
        scheme = "https",
        host = host,
        path = "/$endpoint/${dataType.s}.json"
    )

    enum class DataType(val s: String) { Full("full"), Small("data") }
}
