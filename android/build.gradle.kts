import studio.forface.easygradle.dsl.*
import studio.forface.easygradle.dsl.android.*

plugins {
    id(PluginsDeps.androidLibrary)
    id(PluginsDeps.kotlinAndroid)
}

android()

dependencies {
    api(
        project(Module.domain),
        `coroutines-android`,

        // Android
        `activity`,
        `appcompat`,
        `fluentNotifications`,
        `fragment`,
        `lifecycle-viewModel`,
        `material`,
        `viewStateStore`,
        `android-work-runtime`,

        // Other
        `koin-android`,
        `koin-viewModel`,
        `theia`,
        `kermit`
    )

    implementation(
        project(Module.data)
    )

    testImplementation(project(Module.sharedTest))
    testImplementation(`robolectric`)
}
