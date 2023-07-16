plugins {
    id("com.android.application")           // core
    id("org.jetbrains.kotlin.android")      // core
    id("androidx.navigation.safeargs")      // safe args for navigation
    id("kotlin-kapt")                       // Hilt
    id("com.google.dagger.hilt.android")    // Hilt

    // Symbol Processing: KSP (for Moshi, Room) and Kapt (for Hilt)
//    id("com.google.devtools.ksp")
//    kotlin("kapt")

}

android {
    namespace = "com.vikmanz.shpppro"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.vikmanz.shpppro"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            @Suppress("UnstableApiUsage")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // ViewBinding
    @Suppress("UnstableApiUsage")
    buildFeatures {
        viewBinding = true
    }

    // Hilt uses Java VERSION_1_8 but kaptGenerateStubsDebugKotlin use VERSION_18
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // Hilt uses Java VERSION_1_8 but kaptGenerateStubsDebugKotlin use VERSION_18
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    @Suppress("UnstableApiUsage")
    buildToolsVersion = "33.0.2"

}


dependencies {

    // Core
    implementation (libs.core.ktx)
    implementation (libs.lifecycle.runtime.ktx)
    implementation (libs.activity.compose)
//    implementation (platform(libs.compose.bom))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
//    androidTestImplementation (platform(libs.compose.bom))

    // UI
    implementation (libs.ui)
    implementation (libs.ui.graphics)
    implementation (libs.ui.tooling.preview)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.glide)                      // Glide
    debugImplementation (libs.ui.tooling)
    androidTestImplementation (libs.ui.test.junit4)

    // Lifecycle components
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.common.java8)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.extensions)
//    implementation("android.arch.lifecycle:extensions:1.1.1")

    // Kotlin coroutines components
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)

    // Preferences DataStore
    implementation(libs.androidx.datastore.preferences)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.dynamic.features.fragment)
    implementation(libs.androidx.navigation.testing)
    implementation(libs.androidx.hilt.navigation.fragment) // via Hilt

    // Hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.hilt.compiler)

    // Faker
    implementation(libs.javafaker)       // generator for fake info

    // Retrofit
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)

    // Symbol Processing (for Moshi, Room) and Kapt (for Hilt)
    implementation(libs.symbol.processing.api)
    kapt(libs.hilt.android.compiler)
    //ksp ("com.google.dagger:hilt-android-compiler:2.46.1")

}

// Symbol Processing Kapt (for Hilt)
kapt {
    correctErrorTypes = true // Allow references to generated code
}