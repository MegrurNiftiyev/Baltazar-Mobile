plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.baltazar.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 25
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    // Core
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.androidx.lifecycle.viewmodel.compose)
    api(libs.kotlinx.coroutines.android)

    // Compose
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.activity.compose)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.animation.core)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.icons.extended)
    api(libs.compose.icons.tabler)


    // Navigation
    api(libs.androidx.navigation.compose)
    api(libs.hilt.navigation.compose)

    // Dependency Injection
    api(libs.hilt.android)
    implementation(libs.androidx.compose.material3.lint)
    implementation(libs.compose.material3)
    ksp(libs.hilt.android.compiler)

    // Networking
    api(libs.okhttp.core)
    api(libs.okhttp.logging.interceptor)
    api(libs.retrofit.core)
    api(libs.retrofit.converter.serialization)
    api(libs.kotlinx.serialization.json)

    // Storage
    api(libs.androidx.room.runtime)
    api(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    api(libs.androidx.datastore.preferences)
    api(libs.androidx.security.crypto)

    // Utils
    api(libs.coil.compose)
    api(libs.timber)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}