// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {

//        classpath ("com.android.tools.build:gradle:4.2.2")                          // Kotlin DSL
//        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")               // Kotlin DSL

        classpath (libs.androidx.navigation.safe.args.gradle.plugin)    // navigation
        classpath (libs.hilt.android.gradle.plugin)                     // Hilt

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("com.android.application") version "8.2.0-beta05" apply false                    // core
    id("org.jetbrains.kotlin.android") version "1.8.20-RC2" apply false          // core
    //id("com.android.library") version "8.0.2" apply false                        // core
}
val buildToolsVersion by extra("34.0.0")

//ext {
//    sourceCompatibility = JavaVersion.VERSION_11
//    targetCompatibility = JavaVersion.VERSION_11
//}

//tasks.register("clean", Delete::class){
//    delete(rootProject.buildDir)
//}

true // Needed to make the Suppress annotation work for the plugins block