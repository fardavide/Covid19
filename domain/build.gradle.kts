import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import studio.forface.easygradle.dsl.*

plugins {
    kotlin(PluginsDeps.multiplatform)
    id(PluginsDeps.kotlinSerialization)
}

kotlin {

    jvm()
    js()

    sourceSets {
        all {
            listOf(
                "coroutines.ExperimentalCoroutinesApi",
                "experimental.ExperimentalTypeInference",
                "time.ExperimentalTime"
            ).forEach { languageSettings.useExperimentalAnnotation("kotlin.$it") }
        }

        with(dependencyHandler) {

            val commonMain by getting {
                dependencies {
                    api(
                        `kotlin-common`,
                        `coroutines-core-common`,
                        `okIo`,
                        `kermit`,
                        `koin`,
                        `klock`,
                        `serialization-common`
                    )
                }
            }
            val commonTest by getting {
                dependencies {
                    api(project(Module.sharedTest))
                }
            }


            val jvmMain by getting {
                dependencies {
                    api(
                        `kotlin-jdk8`,
                        `coroutines-core`,
                        `serialization`
                    )
                }
            }
            val jvmTest by getting {
                dependencies {
                    api(
                        `kotlin-test`,
                        `kotlin-test-junit`,
                        `mockk`
                    )
                }
            }

            js().compilations["main"].defaultSourceSet {
                dependencies {
                    api(
                        `kotlin-js`,
                        `coroutines-core-js`,
                        `serialization-js`
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
