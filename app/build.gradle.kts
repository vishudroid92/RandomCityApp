val MAPS_API_KEY: String by project

println("✅ MAPS_API_KEY from gradle.properties = $MAPS_API_KEY")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.dev.randomcityapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dev.randomcityapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        manifestPlaceholders["MAPS_API_KEY"] = MAPS_API_KEY


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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.activity.compose)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.process)

    implementation(libs.navigation.compose)

    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.preview)
    debugImplementation(libs.compose.tooling)
    implementation(libs.compose.runtime.livedata)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    implementation(libs.coroutines.android)

    implementation(libs.work.runtime)

    implementation(libs.maps.compose)
    implementation(libs.maps.sdk)

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.android) // already included
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
    testImplementation(libs.core.testing)
    testImplementation(libs.test.core)
    testImplementation(libs.robolectric)
    testImplementation(libs.coroutines.test)



}