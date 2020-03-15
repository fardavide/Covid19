package studio.forface.covid.domain.entity

interface Id {
    val s: String
}

inline class WorldId(override val s: String): Id
inline class CountryId(override val s: String): Id
inline class ProvinceId(override val s: String): Id

inline class Name(val s: String)

data class Location(
    val lat: Double,
    val lng: Double
)
