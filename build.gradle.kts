import io.micronaut.gradle.MicronautExtension

group = "no.javatec.pubsubemulator"
version = "0.0.1-SNAPSHOT"

repositories { mavenCentral() }

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.micronaut.application) apply false
    alias(libs.plugins.devtools.ksp) apply false
}

val micronautVersion = libs.versions.micronaut.platform.version
val testcontainersGcloudLib = libs.testcontainers.gcloud

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

    dependencies {
        runtimeOnly("org.yaml:snakeyaml")
        implementation("io.micronaut.serde:micronaut-serde-jackson")
        implementation("io.micronaut:micronaut-http-server-netty")
        implementation("io.micronaut.gcp:micronaut-gcp-pubsub")

        testImplementation("io.micronaut:micronaut-http-client")
        testImplementation(testcontainersGcloudLib)
    }
}

