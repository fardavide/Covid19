package studio.forface.covid.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorldStatApiModel(

    @SerialName("id")
    val id: String, // world

    @SerialName("name")
    val name: String, // World

    @SerialName("confirmed")
    val confirmed: Int, // 97886

    @SerialName("deaths")
    val deaths: Int, // 3348

    @SerialName("recovered")
    val recovered: Int, // 53797

    @SerialName("last_update")
    val lastUpdate: String, // 2020-03-05T00:00:00Z

    @SerialName("ts")
    val stats: List<StatApiModel>,

    @SerialName("countries")
    val countryStats: List<CountrySmallStatApiModel>
)

@Serializable
data class WorldFullStatApiModel(

    @SerialName("id")
    val id: String, // world

    @SerialName("name")
    val name: String, // World

    @SerialName("confirmed")
    val confirmed: Int, // 97886

    @SerialName("deaths")
    val deaths: Int, // 3348

    @SerialName("recovered")
    val recovered: Int, // 53797

    @SerialName("last_update")
    val lastUpdate: String, // 2020-03-05T00:00:00Z

    @SerialName("ts")
    val stats: List<StatApiModel>,

    @SerialName("countries")
    val countryStats: List<CountryStatApiModel>
)
