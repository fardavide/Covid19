package studio.forface.covid.android.classic

import org.koin.dsl.module
import studio.forface.covid.android.Router
import studio.forface.covid.android.androidModule

val classicAndroidModule = module {

    factory<Router> { ClassicRouter(context = get()) }

} + androidModule
