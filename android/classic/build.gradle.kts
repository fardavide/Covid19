import studio.forface.easygradle.dsl.android.Version
import studio.forface.easygradle.dsl.android.`constraint-layout`
import studio.forface.easygradle.dsl.dokka
import studio.forface.easygradle.dsl.implementation

plugins {
    id(PluginsDeps.androidApplication)
    id(PluginsDeps.kotlinAndroid)
    id(PluginsDeps.kotlinAndroidExtensions)
}

projectVersion = Version(0, 2)
android("classic")

dependencies {
    implementation(
        project(Module.android),

        // Android
        `constraint-layout`
    )

    testImplementation(project(Module.sharedTest))
}

dokka()
