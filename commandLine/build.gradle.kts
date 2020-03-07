@file:Suppress("LocalVariableName")

import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import studio.forface.easygradle.dsl.*

plugins {
    kotlin(PluginsDeps.jvm)
}

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */

    sourceSets {
        with(dependencyHandler) {

            val main by getting {
                dependencies {
                    implementation(
                        project(Module.domain),

                        `kotlin-jdk8`,
                        `coroutines-core`
                    )
                }
            }
            val test by getting {
                dependencies {
                    implementation(
                        `kotlin-test`,
                        `kotlin-test-junit5`,
                        `coroutines-test`
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
