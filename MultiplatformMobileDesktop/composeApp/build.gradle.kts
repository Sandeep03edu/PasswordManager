import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinxSerialization)

    // moko plugin
    id("dev.icerock.mobile.multiplatform-resources")

    // Sqldelight
    id("app.cash.sqldelight")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true

            // moko library
            export("dev.icerock.moko:resources:0.22.3")
            export("dev.icerock.moko:graphics:0.9.0")
        }
    }

    // Moko MVVM dependencies-
    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export("dev.icerock.moko:mvvm-core:0.16.1")
        }
    }

    sourceSets {
        val desktopMain by getting

        getByName("androidMain").dependsOn(commonMain.get())
        getByName("desktopMain").dependsOn(commonMain.get())
        getByName("iosArm64Main").dependsOn(commonMain.get())
        getByName("iosX64Main").dependsOn(commonMain.get())
        getByName("iosSimulatorArm64Main").dependsOn(commonMain.get())

        androidMain.dependencies {
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            implementation("app.cash.sqldelight:android-driver:2.0.0-rc01")
            implementation(libs.androidx.appcompat)

            // Network Calling
            implementation(libs.ktor.client.okhttp)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0-RC")

            // Cryptographic Library
            implementation("io.github.abhriyaroy:KCrypt:0.0.8")
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.okhttp)

            // Sql Delight Native dependencies
            implementation("app.cash.sqldelight:sqlite-driver:2.0.0-rc01")

        }
        iosMain.dependencies {
            // Network Calling
            implementation(libs.ktor.client.darwin)

            // Sqldelight
            implementation("app.cash.sqldelight:native-driver:2.0.0-rc01")

            // Cryptographic Library
            implementation("io.github.abhriyaroy:KCrypt:0.0.8")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.moko.mvvm.core)
            implementation(libs.moko.mvvm.compose)
            implementation(libs.kamel)

            implementation("app.cash.sqldelight:coroutines-extensions:2.0.0-rc01")

            // moko dependency
            api("dev.icerock.moko:resources:0.22.3")

            // MultiPlatform Setting
            val multiplatformSettings = "1.0.0"
            implementation("com.russhwolf:multiplatform-settings-no-arg:$multiplatformSettings")
            implementation("com.russhwolf:multiplatform-settings-serialization:1.1.0")
            implementation("com.russhwolf:multiplatform-settings-coroutines:$multiplatformSettings")

            // Loading image/Media from url
            implementation("media.kamel:kamel-image:0.8.3")

            // Network Calling
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines.core)


            // DateTime
            implementation(libs.kotlinx.datetime)

            // Multiplatform Navigation
            val voyagerVersion = "1.1.0-alpha02"

            // ScreenModel
            implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion")
            // Navigator
            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
            // BottomSheetNavigator
            implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")
            // TabNavigator
            implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
            // Transitions
            implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

            // Hashing
            implementation("com.appmattus.crypto:cryptohash:0.10.1")

            implementation("org.jetbrains.kotlinx:atomicfu:0.23.2")

            // ModalBottomSheet
            implementation("com.github.skydoves:flexible-bottomsheet-material3:0.1.1")

            // Pull Refresh
            implementation("dev.materii.pullrefresh:pullrefresh:1.2.0")

            // WindowSize Multi Platform
            implementation("dev.chrisbanes.material3:material3-window-size-class-multiplatform:0.3.1")

        }

    }
}

android {
    namespace = "com.sandeep03edu.passwordmanager"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

//    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
//    sourceSets["main"].res.srcDirs("src/androidMain/res")
//    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.sandeep03edu.passwordmanager"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 2
        versionName = "1.01"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.sandeep03edu.passwordmanager"
            packageVersion = "1.0.0"
        }
    }
}


// configuring moko
multiplatformResources {
    // path for shared resources
    multiplatformResourcesPackage = "com.sandeep03edu.passwordmanager"

    // Class name fo shared resources
    multiplatformResourcesClassName = "SharedRes"
}

sqldelight {
    databases {
        create("CredentialDatabase") {
            packageName.set("com.sandeep03edu.passwordmanager.database")
        }
    }
}

