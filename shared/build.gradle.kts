plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("org.jetbrains.compose")

    // moko plugin
    id("dev.icerock.mobile.multiplatform-resources")

    // Serialization for Firebase
    kotlin("plugin.serialization") version "1.9.0"

    // Sqldelight
    id("app.cash.sqldelight")

}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export("dev.icerock.moko:mvvm-core:0.16.1")
        }
    }


    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"

            // moko library
            export("dev.icerock.moko:resources:0.22.3")
            export("dev.icerock.moko:graphics:0.9.0")
        }
    }

    val multiplatformSettings = "1.0.0"

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation("org.jetbrains.kotlinx:atomicfu:0.17.3")
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                implementation(libs.kotlinx.datetime)

                implementation("app.cash.sqldelight:coroutines-extensions:2.0.0-alpha05")

                // moko dependency
                api("dev.icerock.moko:resources:0.22.3")

                // Country Picker
                implementation("com.github.jump-sdk:jetpack_compose_country_code_picker_emoji:2.2.5")

                // Serialization for Firebase
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

                // Firebase
                implementation("dev.gitlive:firebase-auth:1.8.1")
                implementation("dev.gitlive:firebase-firestore:1.8.1")
                implementation("dev.gitlive:firebase-common:1.8.1")


                // MultiPlatform Setting
                implementation("com.russhwolf:multiplatform-settings-no-arg:$multiplatformSettings")
                implementation("com.russhwolf:multiplatform-settings-serialization:1.1.0")
                implementation("com.russhwolf:multiplatform-settings-coroutines:$multiplatformSettings")

                // Loading image/Media from url
                implementation("media.kamel:kamel-image:0.8.3")

                // Network Calling
//                implementation kotlin('stdlib-common')
//                implementation("io.ktor:ktor-client-core:2.2.1")
                implementation(libs.ktor.client.core)
                implementation(libs.kotlinx.coroutines.core)


                // Multiplatform Navigation
                val voyagerVersion = "1.0.0-rc10"
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

                implementation("org.jetbrains.kotlinx:atomicfu:0.20.2")

                // Cryptographic Library
                implementation("io.github.abhriyaroy:KCrypt:0.0.8")

                // ModalBottomSheet
                implementation("com.github.skydoves:flexible-bottomsheet-material3:0.1.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("app.cash.sqldelight:android-driver:2.0.0")

                implementation(platform("com.google.firebase:firebase-bom:30.0.1")) // This line to add the firebase bom

                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.activity.compose)

                // Network Calling
//                implementation("io.ktor:ktor-client-okhttp:2.2.1")
                implementation(libs.ktor.client.okhttp)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0-RC")
            }
        }

        val iosMain by getting {
            dependencies {
                implementation("app.cash.sqldelight:native-driver:2.0.0")

                // Network Calling
//                implementation("io.ktor:ktor-client-darwin:2.2.1")
                implementation(libs.ktor.client.darwin)
//                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.8.0-RC")
            }
            dependsOn(commonMain)
        }
    }
}

android {
    namespace = "com.sandeep03edu.passwordmanager"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    databases {
        create("CredentialDatabase") {
            packageName.set("com.sandeep03edu.passwordmanager.database")
        }
    }
}



dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.compose.material3)
    implementation("androidx.core:core-ktx:+")
    implementation(libs.androidx.appcompat)
    commonMainApi(libs.mvvm.core)
    commonMainApi(libs.mvvm.compose)
    commonMainApi(libs.moko.mvvm.flow)
    commonMainApi(libs.moko.mvvm.flow.compose)

    // Country Picker
    implementation("com.github.jump-sdk:jetpack_compose_country_code_picker_emoji:2.2.5")
    implementation("androidx.compose.material:material:1.5.2")

    // Flow Layout
    implementation("androidx.compose.foundation:foundation:1.4.3")

}

// configuring moko
multiplatformResources {
    // path for shared resources
    multiplatformResourcesPackage = "com.sandeep03edu.passwordmanager"

    // Class name fo shared resources
    multiplatformResourcesClassName = "SharedRes"
}
