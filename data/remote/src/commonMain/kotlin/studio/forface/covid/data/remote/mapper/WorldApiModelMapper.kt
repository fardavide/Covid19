package studio.forface.covid.data.remote.mapper

import studio.forface.covid.data.remote.model.WorldFullStatApiModel
import studio.forface.covid.data.remote.model.WorldStatApiModel
import studio.forface.covid.domain.entity.World
import studio.forface.covid.domain.entity.WorldFullStat
import studio.forface.covid.domain.entity.WorldStat
import studio.forface.covid.domain.entity.plus
import studio.forface.covid.domain.invoke
import studio.forface.covid.domain.mapper.associateMap
import studio.forface.covid.domain.mapper.map

internal class WorldStatApiModelMapper(
    private val worldMapper: WorldFromStatApiModelMapper,
    private val countryMapper: CountrySmallStatApiModelMapper,
    private val statMapper: StatApiModelMapper,
    private val statParamsMapper: StatParamsMapper,
    private val idMapper: CountryIdApiModelMapper
) : ApiModelMapper<WorldStatApiModel, WorldStat> {

    override fun WorldStatApiModel.toEntity(): WorldStat {
        val lastStat = statParamsMapper { StatParams(confirmed, deaths, recovered, lastUpdate).toEntity() }
        val otherStats = stats.map(statMapper) { it.toEntity() }
        return WorldStat(
            world = worldMapper { this@toEntity.toEntity() },
            stats = lastStat + otherStats,
            countryStats = countryStats.associateMap(countryMapper) {
                idMapper { it.id.toEntity() } to it.toEntity()
            }
        )
    }
}

internal class WorldFullStatApiModelMapper(
    private val worldMapper: WorldFromFullStatApiModelMapper,
    private val countryMapper: CountryStatApiModelMapper,
    private val statMapper: StatApiModelMapper,
    private val statParamsMapper: StatParamsMapper,
    private val idMapper: CountryIdApiModelMapper
) : ApiModelMapper<WorldFullStatApiModel, WorldFullStat> {

    override fun WorldFullStatApiModel.toEntity(): WorldFullStat {
        val lastStat = statParamsMapper { StatParams(confirmed, deaths, recovered, lastUpdate).toEntity() }
        val otherStats = stats.map(statMapper) { it.toEntity() }
        return WorldFullStat(
            world = worldMapper { toEntity() },
            stats = lastStat + otherStats,
            countryStats = countryStats.associateMap(countryMapper) {
                idMapper { it.id.toEntity() } to it.toEntity()
            }
        )
    }
}

// TODO replace WorldStatApiModel with WorldApiModel, which does not exist ATM
internal class WorldFromStatApiModelMapper(
    private val idMapper: WorldIdApiModelMapper,
    private val nameMapper: NameApiModelMapper,
    private val countyMapper: CountryFromSmallStatApiModelMapper
) : ApiModelMapper<WorldStatApiModel, World> {

    override fun WorldStatApiModel.toEntity() = World(
        id = idMapper { id.toEntity() },
        name = nameMapper { name.toEntity() },
        countries = countryStats.map(countyMapper) { it.toEntity() }
    )
}

// TODO replace WorldFullStatApiModel with WorldApiModel, which does not exist ATM
internal class WorldFromFullStatApiModelMapper(
    private val countyMapper: CountryFromStatApiModelMapper,
    private val idMapper: WorldIdApiModelMapper,
    private val nameMapper: NameApiModelMapper
) : ApiModelMapper<WorldFullStatApiModel, World> {

    override fun WorldFullStatApiModel.toEntity() = World(
        id = idMapper { id.toEntity() },
        name = nameMapper { name.toEntity() },
        countries = countryStats.map(countyMapper) { it.toEntity() }
    )
}

