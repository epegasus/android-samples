import com.google.protobuf.gradle.proto

plugins {
    id("com.android.application")
    id("com.google.protobuf")
}

android {
    namespace = "com.sohaib.datastore"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.sohaib.datastore"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
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

    sourceSets {
        getByName("main") {
            proto {
                srcDir("src/main/proto")
            }
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.18.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.10.0")

    // DataStore (proto + preferences)
    implementation("androidx.datastore:datastore:1.2.1")
    implementation("androidx.datastore:datastore-preferences:1.2.1")

    // Protobuf runtime
    implementation("com.google.protobuf:protobuf-javalite:4.34.1")

    // Coroutines (DataStore uses them internally)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.34.1"
    }
    generateProtoTasks {
        all().configureEach {
            builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}