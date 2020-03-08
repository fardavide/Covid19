package studio.forface.covid.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatApiModel(

    @SerialName("c")
    val confirmed: Int, // 97886

    @SerialName("d")
    val deaths: Int, // 3348

    @SerialName("r")
    val recovered: Int, // 53797

    @SerialName("t")
    val timestamp: Int // 1583366400
)
