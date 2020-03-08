package studio.forface.covid.domain.usecase

import studio.forface.covid.domain.gateway.Api
import studio.forface.covid.domain.gateway.Repository

class SyncCountries(private val api: Api, private val repository: Repository) {

    suspend operator fun invoke(): Unit = TODO()
}
