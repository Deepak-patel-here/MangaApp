plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "1.9.22"
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.deepakjetpackcompose.mangaapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.deepakjetpackcompose.mangaapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //ktor client
    implementation("io.ktor:ktor-client-core:2.3.6")

    // Ktor Client for Android
    implementation("io.ktor:ktor-client-android:2.3.6")

    // JSON Serialization (for handling JSON responses)
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.6")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // Content Negotiation (for automatic serialization)
    implementation("io.ktor:ktor-client-content-negotiation:2.3.6")

    // Logging (for debugging API requests and responses)
    implementation("io.ktor:ktor-client-logging:2.3.6")

    // SLF4J Logger (for better logging support)
    implementation("org.slf4j:slf4j-simple:2.0.9")

    //lottie animation
    implementation ("com.airbnb.android:lottie-compose:6.4.0")

    //coil for image
    implementation("io.coil-kt:coil-compose:2.5.0")

    val nav_version = "2.8.9"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    //pager
    implementation ("com.google.accompanist:accompanist-pager:0.30.1")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.30.1")

    //statusbar
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.36.0")

    //clock
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")


}