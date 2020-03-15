package studio.forface.covid.data.local

import com.squareup.sqldelight.ColumnAdapter
import studio.forface.covid.domain.entity.CountryId
import studio.forface.covid.domain.entity.Id
import studio.forface.covid.domain.entity.Name
import studio.forface.covid.domain.entity.ProvinceId
import studio.forface.covid.domain.entity.WorldId

internal abstract class AbsIdAdapter<T : Id>(private val toId: (String) -> T) : ColumnAdapter<T, String> {
    override fun decode(databaseValue: String) = toId(databaseValue)
    override fun encode(value: T) = value.s
}

internal class IdAdapter : AbsIdAdapter<Id>({ WorldId(it) /* Any kind of Id is valid */ })
internal class WorldIdAdapter : AbsIdAdapter<WorldId>({ WorldId(it) })
internal class CountryIdAdapter : AbsIdAdapter<CountryId>({ CountryId(it) })
internal class ProvinceIdAdapter : AbsIdAdapter<ProvinceId>({ ProvinceId(it) })

internal class NameAdapter : ColumnAdapter<Name, String> {
    override fun decode(databaseValue: String) = Name(databaseValue)
    override fun encode(value: Name) = value.s
}
