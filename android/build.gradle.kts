import studio.forface.easygradle.dsl.android.`lifecycle-viewModel`
import studio.forface.easygradle.dsl.android.appcompat
import studio.forface.easygradle.dsl.android.fluentNotifications
import studio.forface.easygradle.dsl.android.material
import studio.forface.easygradle.dsl.android.timber
import studio.forface.easygradle.dsl.android.viewStateStore
import studio.forface.easygradle.dsl.api
import studio.forface.easygradle.dsl.implementation

plugins {
    id(PluginsDeps.androidLibrary)
    id(PluginsDeps.kotlinAndroid)
}

android()

dependencies {
    api(
        project(Module.domain),

        // Android
        `activity`,
        `appcompat`,
        `fluentNotifications`,
        `fragment`,
        `lifecycle-viewModel`,
        `material`,
        `viewStateStore`,

        // Other
        `koin-android`,
        `koin-viewModel`,
        `timber`
    )

    implementation(
        project(Module.data)
    )

    testImplementation(project(Module.sharedTest))
}
