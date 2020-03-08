@file:Suppress("ClassName", "ObjectPropertyName", "RemoveRedundantBackticks")

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.kotlin
import studio.forface.easygradle.dsl.*

// Kotlin
val DependencyHandler.`kotlin-test-annotations` get() = kotlin("test-annotations-common")

// Ktor
val DependencyHandler.`ktor-client-core` get() = ktorClientCore()
val DependencyHandler.`ktor-client-core-jvm` get() = ktorClientCore("jvm")
val DependencyHandler.`ktor-client-core-native` get() = ktorClientCore("native")
val DependencyHandler.`ktor-client-android` get() = ktorClient("android")
val DependencyHandler.`ktor-client-apache` get() = ktorClient("apache")
val DependencyHandler.`ktor-client-curl` get() = ktorClient("curl")
val DependencyHandler.`ktor-client-iOs` get() = ktorClient("ios")
val DependencyHandler.`ktor-client-js` get() = ktorClient("js")

// Ktor Json
val DependencyHandler.`ktor-client-json` get() = ktorClientJson()
val DependencyHandler.`ktor-client-json-native` get() = ktorClientJson("native")
val DependencyHandler.`ktor-client-json-js` get() = ktorClientJson("js")
val DependencyHandler.`ktor-client-json-jvm` get() = ktorClientJson("jvm")

// Ktor Serialization
val DependencyHandler.`ktor-client-serialization` get() = ktorClientSerialization()
val DependencyHandler.`ktor-client-serialization-native` get() = ktorClientSerialization("native")
val DependencyHandler.`ktor-client-serialization-js` get() = ktorClientSerialization("js")
val DependencyHandler.`ktor-client-serialization-jvm` get() = ktorClientSerialization("jvm")

// Others
val DependencyHandler.`koinMP` get() = touchLab("koin-core") version `koinMP version`
val DependencyHandler.`klock` get() = korlibs("klock") version `klock version`
val DependencyHandler.`picnic` get() = jakeWharton("picnic") version `picnic version`


// Accessors
fun DependencyHandler.korlibs(groupName: String? = null, module: String? = null, moduleSuffix: String? = null, version: String? = null) =
    dependency("com.soywiz.korlibs", groupName, module, moduleSuffix, version)

fun DependencyHandler.ktor(moduleSuffix: String? = null, version: String? = `ktor version`) =
    dependency("io.ktor", module = "ktor", moduleSuffix = moduleSuffix, version = version)

fun DependencyHandler.ktorClient(moduleSuffix: String? = null, version: String? = `ktor version`) =
    dependency("io.ktor", module = "ktor-client", moduleSuffix = moduleSuffix, version = version)

fun DependencyHandler.ktorClientCore(moduleSuffix: String? = null, version: String? = `ktor version`) =
    dependency("io.ktor", module = "ktor-client-core", moduleSuffix = moduleSuffix, version = version)

fun DependencyHandler.ktorClientJson(moduleSuffix: String? = null, version: String? = `ktor version`) =
    dependency("io.ktor", module = "ktor-client-json", moduleSuffix = moduleSuffix, version = version)

fun DependencyHandler.ktorClientSerialization(moduleSuffix: String? = null, version: String? = `ktor version`) =
    dependency("io.ktor", module = "ktor-client-serialization", moduleSuffix = moduleSuffix, version = version)

fun DependencyHandler.touchLab(module: String? = null, moduleSuffix: String? = null, version: String? = null) =
    dependency("co.touchlab", module = module, moduleSuffix = moduleSuffix, version = version)


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
