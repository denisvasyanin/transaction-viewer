plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "ru.disav.transactionviewer"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.disav.transactionviewer"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            manifestPlaceholders["usesCleartextTraffic"] = true
        }

        release {
            manifestPlaceholders["usesCleartextTraffic"] = false

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)

    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.fragment.ktx)

    kapt(libs.hilt.compiler)
    implementation(libs.hilt.android)

    implementation(libs.viewbindingpropertydelegate)
}