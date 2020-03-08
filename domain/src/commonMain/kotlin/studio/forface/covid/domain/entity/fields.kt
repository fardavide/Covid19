package studio.forface.covid.domain.entity

inline class Id(val s: String)
inline class Name(val s: String)

typealias CountryId = Id
typealias ProvinceId = Id

data class Location(
    val lat: Double,
    val long: Double
)
