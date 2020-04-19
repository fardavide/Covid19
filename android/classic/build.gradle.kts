import studio.forface.easygradle.dsl.android.*
import studio.forface.easygradle.dsl.dokka
import studio.forface.easygradle.dsl.implementation

plugins {
    id(PluginsDeps.androidApplication)
    id(PluginsDeps.kotlinAndroid)
    id(PluginsDeps.kotlinAndroidExtensions)
}

android("classic", Version(0, 2))

dependencies {
    implementation(
        project(Module.android),

        // Android
        `constraint-layout`
    )

    testImplementation(project(Module.sharedTest))
}

dokka()
