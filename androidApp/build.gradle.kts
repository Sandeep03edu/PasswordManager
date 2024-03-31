plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)

    // Firebase
    id("com.google.gms.google-services")
//    id("com.chaquo.python")
}


android {
    namespace = "com.sandeep03edu.passwordmanager.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.sandeep03edu.passwordmanager.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 3
        versionName = "1.01"

        ndk {
            // On Apple silicon, you can omit x86_64.
            abiFilters += listOf("arm64-v8a", "x86_64")
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
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
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.compose.ui.tooling)

    implementation("com.google.firebase:firebase-common-ktx:20.3.3")

    // Kotlin couroutine
    implementation(libs.kotlinx.coroutines.android)
}