plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
    mavenCentral()
    google()
}

dependencies {
    val androidGradlePlugin =   "4.1.0-alpha05" // Released: Apr 13, 2020
    val easyGradle =            "1.2.3-beta-4"  // Released: Mar 01, 2020

    implementation("com.android.tools.build:gradle:$androidGradlePlugin")
    implementation("studio.forface.easygradle:dsl-android:$easyGradle")
}
