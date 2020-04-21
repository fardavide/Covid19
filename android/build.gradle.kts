import studio.forface.easygradle.dsl.android.*
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
        `android-work-runtime`,

        // Other
        `koin-android`,
        `koin-viewModel`,
        `theia`,
        `timber`
    )

    implementation(
        project(Module.data)
    )

    testImplementation(project(Module.sharedTest))
}
