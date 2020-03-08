package studio.forface.covid.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProvinceStatApiModel(

    @SerialName("id")
    val id: String, // iraq

    @SerialName("name")
    val name: String, // Iraq

    @SerialName("Lat")
    val lat: Double, // 33

    @SerialName("Lng")
    val lng: Double, // 44

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
data class ProvinceFullStatApiModel(

    @SerialName("id")
    val id: String, // iraq

    @SerialName("name")
    val name: String, // Iraq

    @SerialName("Lat")
    val lat: Double, // 33

    @SerialName("Lng")
    val lng: Double, // 44

    @SerialName("confirmed")
    val confirmed: Int, // 35

    @SerialName("deaths")
    val deaths: Int, // 2

    @SerialName("recovered")
    val recovered: Int, // 0

    @SerialName("last_update")
    val lastUpdate: String, // 2020-03-05T00:00:00Z

    @SerialName("ts")
    val stats: List<StatApiModel>
)
