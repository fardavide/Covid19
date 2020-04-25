import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.testImplementation
import studio.forface.easygradle.dsl.android.*
import studio.forface.easygradle.dsl.dokka
import studio.forface.easygradle.dsl.implementation

plugins {
    id(PluginsDeps.androidApplication)
    id(PluginsDeps.kotlinAndroid)
    id(PluginsDeps.kotlinAndroidExtensions)
}

projectVersion = Version(0, 3)
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
