@file:Suppress("ObjectPropertyName")

import studio.forface.easygradle.dsl.*
import studio.forface.easygradle.dsl.android.*

fun initVersions() {

    // Kotlin
    `kotlin version` =                      "1.3.70"        // Updated: Mar 03, 2020
    `coroutines version` =                  "1.3.4"         // Updated: Mar 06, 2020
    `serialization version` =               "0.20.0"        // Updated: Mar 04, 2020
    `ktor version` =                        "1.3.1"         // Updated: Feb 26, 2020
    `mockK version` =                       "1.9.3"         // Updated:

    // Android
    `android-gradle-plugin version` =       "3.6.1"         // Updated: Feb 28, 2020
    `appcompat version` =                   "1.1.0"         // Updated: Sep 06, 2019
    `constraint-layout version` =           "2.0.0-beta4"   // Updated: Dec 16, 2019
    `ktx version` =                         "1.2.0"         // Updated: Feb 05, 2020
    `material version` =                    "1.2.0-alpha05" // Updated: Feb 21, 2020
}

const val `picnic version` =                "0.3.0"         // Updated: Feb 18, 2020