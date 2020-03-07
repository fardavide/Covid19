@file:Suppress("LocalVariableName")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import studio.forface.easygradle.dsl.version

initVersions()

buildscript {
    val `kotlin version` = studio.forface.easygradle.dsl.`kotlin version`

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:${`kotlin version`}")

        classpath(kotlin("gradle-plugin", `kotlin version`))
    }
}

allprojects {
    repositories {
        jcenter()
        google()
        mavenCentral()
    }
}

subprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = freeCompilerArgs +
                    "-XXLanguage:+NewInference" +
                    "-Xuse-experimental=kotlin.Experimental"
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
