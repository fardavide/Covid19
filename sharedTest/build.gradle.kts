@file:Suppress("LocalVariableName", "RemoveRedundantBackticks")

import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import studio.forface.easygradle.dsl.*

plugins {
    kotlin(PluginsDeps.multiplatform)
//    id(PluginsDeps.kotlinSerialization)
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
        js()

        with(dependencyHandler) {

            val commonMain by getting {
                dependencies {

                    // Base Dependencies
                    implementation(
                        `kotlin-common`,
                        `coroutines-core-common`
//                        `serialization-common`,
//                        `klock`
                    )

                    // Test Dependencies
                    api(
                        `kotlin-test-common`,
                        `kotlin-test-annotations`,
                        `coroutines-test`
                    )
                }
            }

            val commonTest by getting {
                dependencies {

                    // Base Dependencies
                    implementation(
                        `kotlin-common`,
                        `coroutines-core-common`
//                        `serialization-common`,
//                        `klock`
                    )

                    // Test Dependencies
                    api(
                        `kotlin-test-common`,
                        `kotlin-test-annotations`,
                        `coroutines-test`
                    )
                }
            }

            jvm().compilations["main"].defaultSourceSet {
                dependencies {
                    api(
                        `kotlin-test`,
                        `kotlin-test-junit`
                    )
                }
            }
            jvm().compilations["test"].defaultSourceSet {
                dependencies {
                    api(
                        `kotlin-test`,
                        `kotlin-test-junit`
                    )
                }
            }

            js().compilations["main"].defaultSourceSet {
                dependencies {
                    api(
                        `kotlin-test-js`
                    )
                }
            }
        }
    }
}

// Configuration accessors
fun KotlinDependencyHandler.implementation(vararg dependencyNotations: Any) {
    for (dep in dependencyNotations) implementation(dep)
}

fun KotlinDependencyHandler.api(vararg dependencyNotations: Any) {
    for (dep in dependencyNotations) api(dep)
}
