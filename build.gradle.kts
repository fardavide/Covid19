@file:Suppress("LocalVariableName", "VariableNaming")

import io.gitlab.arturbosch.detekt.DetektPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import studio.forface.easygradle.dsl.version

initVersions()

buildscript {
    val `kotlin version` = studio.forface.easygradle.dsl.`kotlin version`
    val `sqlDelight version` = studio.forface.easygradle.dsl.`sqlDelight version`
    val `agp version` = studio.forface.easygradle.dsl.android.`android-gradle-plugin version`

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", `kotlin version`))

        classpath("org.jetbrains.kotlin:kotlin-serialization:${`kotlin version`}")
        classpath("com.squareup.sqldelight:gradle-plugin:${`sqlDelight version`}")
        classpath("com.android.tools.build:gradle:${`agp version`}")
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
        maven(url = "https://dl.bintray.com/touchlabpublic/kotlin")
    }
}

subprojects {
    projectVersion = studio.forface.easygradle.dsl.android.Version(0, 0)

    // Setup KDoc and archives
    if (!name.contains("test", ignoreCase = true) && name != "buildSrc") {

        val generateReleases = {
            with(ReleaseManager) {
                moveApk("releases")
                generateKdocIfNeeded()
                updateReadme()
            }
        }

        tasks.create("generateReleases") {
            doLast {
                generateReleases()
            }
        }

        afterEvaluate {
            tasks.getByName("assemble").doLast {
                generateReleases()
            }
        }
    }

    // Options for Kotlin
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = freeCompilerArgs + arrayOf(
                "-XXLanguage:+NewInference",
                "-Xuse-experimental=kotlin.Experimental",
                "-Xuse-experimental=kotlin.time.ExperimentalTime",
                "-Xopt-in=kotlin.RequiresOptIn"
            )
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
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
