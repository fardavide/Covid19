@file:Suppress("LocalVariableName")

import io.gitlab.arturbosch.detekt.DetektPlugin
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

plugins {
    id(PluginsDeps.detekt) version studio.forface.easygradle.dsl.`detekt version`
}

allprojects {
    repositories {
        jcenter()
        google()
        mavenCentral()
    }
}

subprojects {

    // Options for Kotlin
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = freeCompilerArgs +
                    "-XXLanguage:+NewInference" +
                    "-Xuse-experimental=kotlin.Experimental"
        }
    }

    // Disable JavaDoc
    tasks.withType<Javadoc> { enabled = false }

    // Configure Detekt
    apply<DetektPlugin>()

    detekt {
        failFast = false // fail build on any finding
        buildUponDefaultConfig = true // preconfigure defaults
        config = files("$rootDir/config/detekt.yml") // point to your custom config defining rules to run
//        baseline = file("$rootDir/config/baseline.xml") // a way of suppressing issues before introducing detekt

        reports {
            html.enabled = true // observe findings in your browser with structure and code snippets
            xml.enabled = false // checkstyle like format mainly for integrations like Jenkins
            txt.enabled = false // similar to the console output, contains issue signature to edit baseline files
        }
    }
    // Kotlin options
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
