@file:Suppress("ObjectPropertyName")

import studio.forface.easygradle.dsl.`coroutines version`
import studio.forface.easygradle.dsl.`detekt version`
import studio.forface.easygradle.dsl.`kotlin version`
import studio.forface.easygradle.dsl.`ktor version`
import studio.forface.easygradle.dsl.`mockK version`
import studio.forface.easygradle.dsl.`serialization version`
import studio.forface.easygradle.dsl.`sqlDelight version`
import studio.forface.easygradle.dsl.android.`android-gradle-plugin version`
import studio.forface.easygradle.dsl.android.`appcompat version`
import studio.forface.easygradle.dsl.android.`constraint-layout version`
import studio.forface.easygradle.dsl.android.`ktx version`
import studio.forface.easygradle.dsl.android.`material version`

fun initVersions() {

    // Kotlin
    `kotlin version` =                      "1.3.61"        // Released: Mar 03, 2020 // TODO 1.3.70 is not compatible with Ktor 1.3.1
    `coroutines version` =                  "1.3.4"         // Released: Mar 06, 2020
    `serialization version` =               "0.14.0"        // Released: Mar 04, 2020 // TODO 0.20.0 is not compatible with Ktor 1.3.1
    `ktor version` =                        "1.3.1"         // Released: Feb 26, 2020

    // Android
    `android-gradle-plugin version` =       "3.6.1"         // Released: Feb 28, 2020
    `appcompat version` =                   "1.1.0"         // Released: Sep 06, 2019
    `constraint-layout version` =           "2.0.0-beta4"   // Released: Dec 16, 2019
    `ktx version` =                         "1.2.0"         // Released: Feb 05, 2020
    `material version` =                    "1.2.0-alpha05" // Released: Feb 21, 2020

    // Others
    `detekt version` =                      "1.6.0"         // Released: Feb 26, 2020
    `mockK version` =                       "1.9.3"         // Released:
    `sqlDelight version` =                  "1.2.2"         // Released: Jan 23, 2020
}

const val `clikt version` =                 "2.6.0"         // Released: Mar 17, 2020
const val `klock version` =                 "1.8.9"         // Released: Feb 18, 2020
const val `koin version` =                  "3.0.0-alpha-8" // Released: ???
const val `picnic version` =                "0.3.0"         // Released: Feb 18, 2020
