import org.gradle.kotlin.dsl.testImplementation
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
        `timber`
    )

    implementation(
        project(Module.data)
    )

    testImplementation(project(Module.sharedTest))
}
