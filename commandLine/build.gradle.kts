@file:Suppress("LocalVariableName", "RemoveRedundantBackticks")

import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

plugins {
    kotlin(PluginsDeps.jvm)
}

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */

    sourceSets {
        all {
            listOf(
                "experimental.ExperimentalTypeInference", "time.ExperimentalTime"
            ).forEach { languageSettings.useExperimentalAnnotation("kotlin.$it") }
        }

        with(dependencyHandler) {

            val main by getting {
                dependencies {
                    implementation(
                        project(Module.domain),
                        project(Module.data),

                        `clikt-jvm`,
                        `picnic`
                    )
                }
            }
            val test by getting {
                dependencies {

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
