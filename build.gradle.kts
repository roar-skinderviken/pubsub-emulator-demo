import io.micronaut.gradle.MicronautExtension

group = "no.javatec.pubsubemulator"
version = "0.0.1-SNAPSHOT"

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.micronaut.application) apply false
    alias(libs.plugins.devtools.ksp) apply false
}

val micronautVersion = libs.versions.micronaut.platform.version

subprojects {
    repositories { mavenCentral() }

    apply(plugin = "io.micronaut.application")

    if (name == "kotlin-example") {
        apply(plugin = "org.jetbrains.kotlin.jvm")
        apply(plugin = "com.google.devtools.ksp")
    }

    configure<MicronautExtension> {
        version = micronautVersion
        runtime("netty")
        processing {
            incremental(true)
        }
    }
}

