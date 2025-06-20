group = "no.javatec.pubsubemulator"
version = "0.0.1-SNAPSHOT"

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.micronaut.application) apply false
    alias(libs.plugins.devtools.ksp) apply false
}

subprojects {
    repositories {
        mavenCentral()

        if (name == "kotlin-example") {
            apply(plugin = "org.jetbrains.kotlin.jvm")
            apply(plugin = "com.google.devtools.ksp")
        }

        apply(plugin = "io.micronaut.application")
    }
}

