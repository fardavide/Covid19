package studio.forface.covid.android.classic

import org.koin.core.module.Module
import studio.forface.covid.android.AbsApp

class App : AbsApp() {

    /** This should be implemented with a [Module] for `studio.forface.covid.android.classic` or
     * `studio.forface.covid.android.compose` */
    override val androidImplModule = classicAndroidModule
}
