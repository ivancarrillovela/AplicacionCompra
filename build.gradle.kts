// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath ("io.realm:realm-gradle-plugin:10.15.1")
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
}