import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

plugins {
    kotlin(PluginsDeps.multiplatform)
    id(PluginsDeps.kotlinSerialization)
}

kotlin {

    jvm()
    // js()


    sourceSets {

        with(dependencyHandler) {

            val commonMain by getting {
                dependencies {
                    implementation(
                        project(Module.domain),
                        project(Module.remoteData),
                        project(Module.localData)
                    )
                }
            }

            jvm().compilations["main"]
//            js().compilations["main"]

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
