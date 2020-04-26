import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import studio.forface.easygradle.dsl.*

plugins {
    kotlin(PluginsDeps.multiplatform)
    id(PluginsDeps.androidLibrary)
    id(PluginsDeps.kotlinSerialization)
}

android()

kotlin {

    jvm()
    android()
    // js()

    sourceSets {

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

                        `ktor-client-apache`,
                        `ktor-client-core-jvm`,
                        `ktor-client-json-jvm`,
                        `ktor-client-serialization-jvm`
                    )
                }
            }

            val androidMain by getting {
                dependencies {
                    implementation(
                        `serialization`,

                        `ktor-client-android`,
                        `ktor-client-core-jvm`,
                        `ktor-client-json-jvm`,
                        `ktor-client-serialization-jvm`
                    )
                }
            }

//            js().compilations["main"].defaultSourceSet {
//                dependencies {
//                    implementation(
//                        `ktor-client-js`,
//                        `ktor-client-json-js`,
//                        `ktor-client-serialization-js`
//                    )
//                }
//            }

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
