package studio.forface.covid.data.local

import studio.forface.covid.domain.gateway.Repository

/**
 * Default Implementation of [Repository]
 * @author Davide Farella
 */
internal class RepositoryImpl(
    private val country: CountryQueries,
    private val province: ProvinceQueries,
    private val stat: StatQueries
) : Repository
