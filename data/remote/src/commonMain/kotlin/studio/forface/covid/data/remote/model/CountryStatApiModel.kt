package studio.forface.covid.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountrySmallStatApiModel(

    @SerialName("id")
    val id: String, // iraq

    @SerialName("name")
    val name: String, // Iraq

    @SerialName("confirmed")
    val confirmed: Int, // 35

    @SerialName("deaths")
    val deaths: Int, // 2

    @SerialName("recovered")
    val recovered: Int, // 0

    @SerialName("last_update")
    val lastUpdate: String // 2020-03-05T00:00:00Z
)

@Serializable
data class CountryStatApiModel(

    @SerialName("id")
    val id: String, // iraq

    @SerialName("name")
    val name: String, // Iraq

    @SerialName("confirmed")
    val confirmed: Int, // 35

    @SerialName("deaths")
    val deaths: Int, // 2

    @SerialName("recovered")
    val recovered: Int, // 0

    @SerialName("last_update")
    val lastUpdate: String, // 2020-03-05T00:00:00Z

    @SerialName("ts")
    val stats: List<StatApiModel>,

    @SerialName("provinces")
    val provinceStats: List<ProvinceStatApiModel>
)

@Serializable
data class CountryFullStatApiModel(

    @SerialName("id")
    val id: String, // iraq

    @SerialName("name")
    val name: String, // Iraq

    @SerialName("confirmed")
    val confirmed: Int, // 35

    @SerialName("deaths")
    val deaths: Int, // 2

    @SerialName("recovered")
    val recovered: Int, // 0

    @SerialName("last_update")
    val lastUpdate: String, // 2020-03-05T00:00:00Z

    @SerialName("ts")
    val stats: List<StatApiModel>,

    @SerialName("provinces")
    val provinceStats: List<ProvinceFullStatApiModel>
)
