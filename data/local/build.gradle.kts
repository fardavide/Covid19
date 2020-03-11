@file:Suppress("LocalVariableName", "RemoveRedundantBackticks")

import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import studio.forface.easygradle.dsl.*

plugins {
    kotlin(PluginsDeps.multiplatform)
//    id(PluginsDeps.androidLibrary)
    id(PluginsDeps.kotlinSerialization)
    id(PluginsDeps.sqlDelight)
}

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */

    sourceSets {
        all {
            listOf(
                "TypeInference", "Time"
            ).forEach { languageSettings.useExperimentalAnnotation("kotlin.time.Experimental$it") }
        }

        jvm()
//        android()
//        js()

        with(dependencyHandler) {

            val commonMain by getting {
                dependencies {
                    implementation(
                        project(Module.domain),

                        `klock`,
                        `serialization-common`
                    )
                }
            }
            val commonTest by getting {
                dependencies {
                    implementation(project(Module.sharedTest))
                }
            }

            jvm().compilations["main"].defaultSourceSet {
                dependencies {
                    implementation(
                        `sqlDelight-sqlite-driver`
                    )
                }
            }
//            android().compilations["main"].defaultSourceSet {
//                dependencies {
//                    implementation(
//                        `sqlDelight-android-driver`
//                    )
//                }
//            }
//            js().compilations["main"].defaultSourceSet {
//                dependencies {
//                    implementation(
//                        // FIXME: remove version when sync with other SqlDelight artifacts
//                        `sqlDelight-sqljs-driver` version "1.3.0"
//                    )
//                }
//            }

        }
    }
}

sqldelight {
    database("Database") {
        packageName = "studio.forface.covid.data.local"
    }
}

// Configuration accessors
fun KotlinDependencyHandler.implementation(vararg dependencyNotations: Any) {
    for (dep in dependencyNotations) implementation(dep)
}

fun KotlinDependencyHandler.api(vararg dependencyNotations: Any) {
    for (dep in dependencyNotations) api(dep)
}
