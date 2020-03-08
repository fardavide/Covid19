@file:Suppress("ClassName", "ObjectPropertyName", "RemoveRedundantBackticks")

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.kotlin
import studio.forface.easygradle.dsl.*

// Kotlin
val DependencyHandler.`kotlin-test-annotations` get() = kotlin("test-annotations-common")

// Others
val DependencyHandler.`klock` get() = korlibs("klock") version `klock version`
val DependencyHandler.`picnic` get() = jakeWharton("picnic") version `picnic version`


// Accessors
fun DependencyHandler.korlibs(groupName: String? = null, module: String? = null, moduleSuffix: String? = null, version: String? = null) =
    dependency("com.soywiz.korlibs", groupName, module, moduleSuffix, version)


object Deps {

    object Js {
        val stdLib = "stdlib-js"
        val test = "test-js"
        val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js" version `coroutines version`
        val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime-js" version `serialization version`
    }

    object Jvm : DependencyHandler by dependencyHandler {
        val stdLib = "stdlib-jdk8"
        val test = "test"
        val testJUnit = "test-junit"
        val reflection = "reflect"
        val coroutinesCore = `coroutines-core`
        val coroutinesJdk8 = `coroutines-jdk8`
        val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime" version `serialization version`
        val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test" version `coroutines version`
        val mockK = "io.mockk:mockk" version `mockK version`
    }

    object iOs {
        val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime-native" version `serialization version`
        val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native" version `coroutines version`
    }

    object Native {
        val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-runtime-native" version `serialization version`
        val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native" version `coroutines version`
    }
}

object PluginsDeps {
    object Versions {
        const val spotlessVersion = "3.24.2"
    }

    const val kotlinSerialization = "kotlinx-serialization"
    const val multiplatform = "multiplatform"
    const val jvm = "jvm"
    const val detekt = "io.gitlab.arturbosch.detekt"

    const val mavenPublish = "maven-publish"
    const val signing = "signing"
    const val dokka = "org.jetbrains.dokka"
    const val spotless = "com.diffplug.gradle.spotless"
}
