plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.sohaib.zoommeeting"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.sohaib.zoommeeting"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        ndk {
            abiFilters.removeAll(listOf("x86", "x86_64"))
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":mobilertc"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // JWT Token
    implementation(libs.java.jwt)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
}