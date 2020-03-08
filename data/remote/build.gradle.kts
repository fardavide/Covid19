@file:Suppress("LocalVariableName", "RemoveRedundantBackticks")

import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import studio.forface.easygradle.dsl.*

plugins {
    kotlin(PluginsDeps.multiplatform)
    id(PluginsDeps.kotlinSerialization)
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
                    implementation(
                        project(Module.domain),

                        `klock`,
                        `serialization-common`,

                        `ktor-client-core`,
                        `ktor-client-json`,
                        `ktor-client-serialization`
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
                        `serialization`,

                        `ktor-client-core-jvm`,
                        `ktor-client-apache`,
                        `ktor-client-json-jvm`,
                        `ktor-client-serialization-jvm`
                    )
                }
            }

            js().compilations["main"].defaultSourceSet {
                dependencies {
                    implementation(
                        `ktor-client-js`,
                        `ktor-client-json-js`,
                        `ktor-client-serialization-js`
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
