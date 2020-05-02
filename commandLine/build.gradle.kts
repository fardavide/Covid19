import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import studio.forface.easygradle.dsl.*

plugins {
    kotlin(PluginsDeps.jvm)
}

kotlin {

    sourceSets {

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

dokka()
