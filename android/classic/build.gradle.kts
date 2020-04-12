import studio.forface.easygradle.dsl.android.`constraint-layout`
import studio.forface.easygradle.dsl.implementation

plugins {
    id(PluginsDeps.androidApplication)
    id(PluginsDeps.kotlinAndroid)
    id(PluginsDeps.kotlinAndroidExtensions)
}

android("classic")

dependencies {
    implementation(
        project(Module.android),

        // Android
        `constraint-layout`
    )

    testImplementation(project(Module.sharedTest))
}
