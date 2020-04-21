@file:Suppress("ObjectPropertyName")

import studio.forface.easygradle.dsl.*
import studio.forface.easygradle.dsl.android.*

fun initVersions() {

    // Kotlin
    `kotlin version` =                      "1.3.61"        // Released: Mar 03, 2020 // TODO 1.3.70 is not compatible with Ktor 1.3.1
    `coroutines version` =                  "1.3.4"         // Released: Mar 06, 2020
    `serialization version` =               "0.14.0"        // Released: Mar 04, 2020 // TODO 0.20.0 is not compatible with Ktor 1.3.1
    `ktor version` =                        "1.3.1"         // Released: Feb 26, 2020

    // Android
    `android-gradle-plugin version` =       "4.1.0-alpha06" // Released: Apr 19, 2020
    `appcompat version` =                   "1.2.0-beta01"  // Released: Apr 05, 2020
    `constraint-layout version` =           "2.0.0-beta4"   // Released: Dec 16, 2019
    `ktx version` =                         "1.3.0-beta01"  // Released: Apr 05, 2020
    `material version` =                    "1.2.0-alpha05" // Released: Feb 21, 2020
    `lifecycle version` =                   "2.3.0-alpha01" // Released: Mar 06, 2020
    `android-work version` =                "2.4.0-alpha02" // Released: Apr 05, 2020

    // Others
    `detekt version` =                      "1.6.0"         // Released: Feb 26, 2020
    `fluentNotifications version` =         "0.1-alpha-6"   // Released: May 26, 2019
    `mockK version` =                       "1.9.3"         // Released:
    `sqlDelight version` =                  "1.2.2"         // Released: Jan 23, 2020
    `viewStateStore version` =              "1.4-beta-4"    // Released: Mar 02, 2020
}

// Kotlin
const val `kotlinIo version` =              "0.1.16"        // Release: Nov 20, 2019

// Android
const val `activity version` =              "1.2.0-alpha03" // Released: Apr 05, 2020
const val `androidUi version` =             "0.1.0-dev08"   // Released: Apr 03, 2020
const val `fragment version` =              "1.2.0-alpha03" // Released: Apr 05, 2020

// Others
const val `clikt version` =                 "2.6.0"         // Released: Mar 17, 2020
const val `klock version` =                 "1.10.0"        // Released: Mar 07, 2020
const val `koin version` =                  "2.1.5"         // Released: Mar 23, 2020
const val `koin3 version` =                 "3.0.0-alpha-8" // Released: ???
const val `okIo version` =                  "2.5.0"         // Released: Mar 21, 2020
const val `picnic version` =                "0.3.0"         // Released: Feb 18, 2020
