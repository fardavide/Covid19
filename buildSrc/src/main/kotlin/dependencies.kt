@file:Suppress("ClassName", "ObjectPropertyName", "RemoveRedundantBackticks")

import org.gradle.api.artifacts.dsl.DependencyHandler
import studio.forface.easygradle.dsl.*
import studio.forface.easygradle.dsl.android.androidx

// region Kotlin
val DependencyHandler.`kotlin-io` get() = kotlinIo()
val DependencyHandler.`kotlin-io-jvm` get() = kotlinIo("jvm")
val DependencyHandler.`kotlin-test-annotations` get() = kotlin("test-annotations-common")
// endregion

// region Ktor
val DependencyHandler.`ktor-client-core` get() = ktorClientCore()
val DependencyHandler.`ktor-client-core-jvm` get() = ktorClientCore("jvm")
val DependencyHandler.`ktor-client-core-native` get() = ktorClientCore("native")
val DependencyHandler.`ktor-client-android` get() = ktorClient("android")
val DependencyHandler.`ktor-client-apache` get() = ktorClient("apache")
val DependencyHandler.`ktor-client-curl` get() = ktorClient("curl")
val DependencyHandler.`ktor-client-iOs` get() = ktorClient("ios")
val DependencyHandler.`ktor-client-js` get() = ktorClient("js")

// region Ktor Json
val DependencyHandler.`ktor-client-json` get() = ktorClientJson()
val DependencyHandler.`ktor-client-json-native` get() = ktorClientJson("native")
val DependencyHandler.`ktor-client-json-js` get() = ktorClientJson("js")
val DependencyHandler.`ktor-client-json-jvm` get() = ktorClientJson("jvm")
// endregion

// region Ktor Serialization
val DependencyHandler.`ktor-client-serialization` get() = ktorClientSerialization()
val DependencyHandler.`ktor-client-serialization-native` get() = ktorClientSerialization("native")
val DependencyHandler.`ktor-client-serialization-js` get() = ktorClientSerialization("js")
val DependencyHandler.`ktor-client-serialization-jvm` get() = ktorClientSerialization("jvm")
// endregion
// endregion

// region SqlDelight extensions
val DependencyHandler.`sqlDelight-coroutines` get() = sqlDelight("coroutines-extensions")
val DependencyHandler.`sqlDelight-paging` get() = sqlDelight("android-paging-extensions")
// endregion

// region Android

// region Compose
val DependencyHandler.`androidUi-android-text` get() = androidUi("android-text")
val DependencyHandler.`androidUi-animation` get() = androidUi("animation")
val DependencyHandler.`androidUi-animation-core` get() = androidUi("animation-core")
val DependencyHandler.`androidUi-core` get() = androidUi("core")
val DependencyHandler.`androidUi-foundation` get() = androidUi("foundation")
val DependencyHandler.`androidUi-framework` get() = androidUi("framework")
val DependencyHandler.`androidUi-geometry` get() = androidUi("geometry")
val DependencyHandler.`androidUi-graphics` get() = androidUi("graphics")
val DependencyHandler.`androidUi-layout` get() = androidUi("layout")
val DependencyHandler.`androidUi-material` get() = androidUi("material")
val DependencyHandler.`androidUi-material-icons-core` get() = androidUi("material-icons-core")
val DependencyHandler.`androidUi-material-icons-extended` get() = androidUi("material-icons-extended")
val DependencyHandler.`androidUi-platform` get() = androidUi("platform")
val DependencyHandler.`androidUi-test` get() = androidUi("test")
val DependencyHandler.`androidUi-text` get() = androidUi("text")
val DependencyHandler.`androidUi-tooling` get() = androidUi("tooling")
val DependencyHandler.`androidUi-unit` get() = androidUi("unit")
val DependencyHandler.`androidUi-util` get() = androidUi("util")
val DependencyHandler.`androidUi-vector` get() = androidUi("vector")
// endregion
// endregion

// region Others
val DependencyHandler.`clikt` get() = clikt("multiplatform")
val DependencyHandler.`clikt-jvm` get() = clikt()
val DependencyHandler.`kermit` get() = touchLab("kermit") version `kermit version`
val DependencyHandler.`koin` get() = koin("core") version `koin3 version`
val DependencyHandler.`koin-android` get() = koin("android")
val DependencyHandler.`koin-test` get() = koin("test")
val DependencyHandler.`koin-viewModel` get() = koin("androidx-viewmodel")
val DependencyHandler.`klock` get() = klock()
val DependencyHandler.`klock-android` get() = klock("android")
val DependencyHandler.`okIo` get() = squareup("okio", moduleSuffix = "multiplatform") version `okIo version`
val DependencyHandler.`picnic` get() = jakeWharton("picnic") version `picnic version`
// endregion


// region Accessors
fun DependencyHandler.androidUi(moduleSuffix: String, version: String = `androidUi version`) =
    androidx("ui", module = "ui", moduleSuffix = moduleSuffix, version = version)

fun DependencyHandler.clikt(moduleSuffix: String? = null, version: String? = `clikt version`) =
    dependency("com.github.ajalt", module = "clikt", moduleSuffix = moduleSuffix, version = version)

fun DependencyHandler.klock(moduleSuffix: String? = null, version: String? = `klock version`) =
    korlibs("klock", moduleSuffix = moduleSuffix, version = version)

fun DependencyHandler.koin(moduleSuffix: String, version: String = `koin version`) =
    dependency("org.koin", module = "koin", moduleSuffix = moduleSuffix, version = version)

fun DependencyHandler.korlibs(
    groupName: String? = null,
    module: String? = null,
    moduleSuffix: String? = null,
    version: String? = null
) = dependency("com.soywiz.korlibs", groupName, module, moduleSuffix, version)

fun DependencyHandler.kotlinIo(moduleSuffix: String? = null, version: String? = `kotlinIo version`) =
    kotlinx(moduleSuffix = "io${moduleSuffix?.let { "-$it" } ?: ""}", version = version)

fun DependencyHandler.ktor(moduleSuffix: String? = null, version: String = `ktor version`) =
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
// endregion


// region plugins
object PluginsDeps {
    // Kotlin
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kotlinSerialization = "kotlinx-serialization"
    const val multiplatform = "multiplatform"
    const val jvm = "jvm"

    // Android
    const val androidLibrary = "com.android.library"
    const val androidApplication = "com.android.application"

    // Other
    const val sqlDelight = "com.squareup.sqldelight"
    const val detekt = "io.gitlab.arturbosch.detekt"

    const val mavenPublish = "maven-publish"
    const val signing = "signing"
    const val dokka = "org.jetbrains.dokka"
    const val spotless = "com.diffplug.gradle.spotless"
}
// endregion
