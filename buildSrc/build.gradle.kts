plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
    mavenCentral()
    google()
}

dependencies {
    val androidGradlePlugin =   "4.1.0-alpha04" // Released: Mar 24, 2020
    val easyGradle =            "1.2.3-beta-4"  // Updated: Mar 01, 2020

    implementation("com.android.tools.build:gradle:$androidGradlePlugin")
    implementation("studio.forface.easygradle:dsl-android:$easyGradle")
}
