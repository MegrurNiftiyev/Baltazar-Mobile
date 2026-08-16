import java.io.FileInputStream
import java.util.Properties

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        load(FileInputStream(file))
    }
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.secrets)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.baltazar.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.baltazar.app"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["MAPS_API_KEY"] = localProperties.getProperty("MAPS_API_KEY") ?: ""
    }

    signingConfigs {
        create("dev") {
            val keystorePath =
                localProperties.getProperty("DEV_KEYSTORE_PATH") ?: "baltazar-dev.jks"
            storeFile = rootProject.file(keystorePath)
            storePassword = localProperties.getProperty("DEV_KEYSTORE_PASSWORD")
                ?: System.getenv("DEV_KEYSTORE_PASSWORD")
            keyAlias = localProperties.getProperty("DEV_KEY_ALIAS")
                ?: System.getenv("DEV_KEY_ALIAS")
            keyPassword = localProperties.getProperty("DEV_KEY_PASSWORD")
                ?: System.getenv("DEV_KEY_PASSWORD")
        }
        create("prod") {
            val keystorePath =
                localProperties.getProperty("PROD_KEYSTORE_PATH") ?: "baltazar-prod.jks"
            storeFile = rootProject.file(keystorePath)
            storePassword = localProperties.getProperty("PROD_KEYSTORE_PASSWORD")
                ?: System.getenv("PROD_KEYSTORE_PASSWORD")
            keyAlias = localProperties.getProperty("PROD_KEY_ALIAS")
                ?: System.getenv("PROD_KEY_ALIAS")
            keyPassword = localProperties.getProperty("PROD_KEY_PASSWORD")
                ?: System.getenv("PROD_KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions { jvmTarget = "11" }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            signingConfig = signingConfigs.getByName("dev")
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://baltazar-backend-kf2f.onrender.com/\""
            )
            buildConfigField(
                "String",
                "PAYMENT_BASE_URL",
                "\"https://baltazar-backend-payment.onrender.com/\""
            )
        }
        create("prod") {
            dimension = "environment"
            signingConfig = signingConfigs.getByName("prod")
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://baltazar-backend-production.onrender.com/\""
            )
            buildConfigField(
                "String",
                "PAYMENT_BASE_URL",
                "\"https://baltazar-backend-payment.onrender.com/\""
            )
        }
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:food"))
    implementation(project(":feature:travel"))
    implementation(project(":feature:rentacar"))
    implementation(project(":feature:hotel"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:explore"))
    implementation(project(":feature:order"))
    implementation(project(":feature:company"))

    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.compose.icons.tabler)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    debugImplementation(libs.leakcanary.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}