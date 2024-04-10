plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}

buildscript {
    // moko library
    dependencies {
        classpath("dev.icerock.moko:resources-generator:0.23.0")
        classpath(libs.sqldelight.gradle.plugin)
        classpath("org.jetbrains.kotlinx:atomicfu-gradle-plugin:0.17.3")
    }

    repositories {
        mavenCentral()
        maven {
            url = uri("https://repo.repsy.io/mvn/chrynan/public")
        }
    }
}

allprojects {
    apply(plugin = "kotlinx-atomicfu")
}