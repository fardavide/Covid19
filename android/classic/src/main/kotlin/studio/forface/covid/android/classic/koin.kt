package studio.forface.covid.android.classic

import org.koin.dsl.module
import studio.forface.covid.android.androidModule
import studio.forface.covid.domain.ConfigurationNameQualifier
import studio.forface.covid.domain.entity.BaseVersion
import studio.forface.covid.domain.usecase.updates.BuildDownloadableUpdateFileName
import studio.forface.covid.domain.usecase.updates.GetAppVersion

val classicAndroidModule = module {

    factory(ConfigurationNameQualifier) { BuildConfig.CONFIGURATION_NAME }

    single<BuildDownloadableUpdateFileName> {
        object : BuildDownloadableUpdateFileName {
            override fun invoke(versionName: String) =
                "Covid-${BuildConfig.CONFIGURATION_NAME}_$versionName-${BuildConfig.BUILD_TYPE}.apk"
        }
    }

    single<GetAppVersion> {
        object : GetAppVersion {
            override fun invoke() = BaseVersion(BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME)
        }
    }

} + androidModule
