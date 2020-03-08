package studio.forface.covid

import org.koin.dsl.module
import studio.forface.covid.data.dataModule
import studio.forface.covid.domain.domainModule
import studio.forface.covid.domain.plus

internal val appModule = module {

} + domainModule + dataModule
