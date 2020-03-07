@file:Suppress("LocalVariableName")

import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import studio.forface.easygradle.dsl.*

plugins {
    kotlin(PluginsDeps.multiplatform)
    id(PluginsDeps.kotlinSerializationPlugin)
}

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */

    sourceSets {
        with(dependencyHandler) {

            val commonMain by getting {
                dependencies {
                    implementation(
                        `kotlin-common`,
                        `coroutines-core-common`,
                        `serialization-common`
                    )
                }
            }
            val commonTest by getting {
                dependencies {
                    implementation(
                        `kotlin-test-common`,
                        `kotlin-test-annotations`,
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
