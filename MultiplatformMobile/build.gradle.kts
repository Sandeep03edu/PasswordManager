plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)

    // Firebase
    id("com.google.gms.google-services") version "4.4.0" apply false

    // Chaquo Android
//    id("com.chaquo.python") version "15.0.1" apply false
}

buildscript {
//    val kotlin_version by extra("2.0.0-Beta1")
    // moko library
    dependencies {
        classpath("dev.icerock.moko:resources-generator:0.23.0")
        classpath(libs.sqldelight.gradle.plugin)
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("org.jetbrains.kotlinx:atomicfu-gradle-plugin:0.17.3")

        // Chaquo Dependency
//        classpath("com.chaquo.python:gradle:15.0.1")
    }
    repositories {
        mavenCentral()
        maven {
            url = uri("https://repo.repsy.io/mvn/chrynan/public")
        }
//        maven {
//            url = uri("https://chaquo.com/maven")
//        }

    }
}

allprojects {
    apply(plugin = "kotlinx-atomicfu")
}