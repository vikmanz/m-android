// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath (libs.androidx.navigation.safe.args.gradle.plugin)    // navigation
        classpath (libs.hilt.android.gradle.plugin)                     // Hilt
    }
}

plugins {
    id("com.android.application") version "8.2.1" apply false                    // core
    id("org.jetbrains.kotlin.android") version "1.8.20-RC2" apply false          // core
    //id("com.android.library") version "8.0.2" apply false                      // core
}
val buildToolsVersion by extra("34.0.0")

true // Needed to make the Suppress annotation work for the plugins block