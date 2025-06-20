plugins {
    java
    id("groovy")
}

application {
    mainClass = "no.javatec.pubsubemulator.spock.Application"
}

java {
    toolchain { languageVersion = JavaLanguageVersion.of(21) }
}

dependencies {
    runtimeOnly("org.yaml:snakeyaml")
    runtimeOnly("ch.qos.logback:logback-classic")

    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut.gcp:micronaut-gcp-pubsub")

    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation(libs.testcontainers.gcloud)
}

micronaut {
    version = libs.versions.micronaut.platform.version
    runtime("netty")
    testRuntime("spock2")
    processing {
        incremental(true)
        annotations("no.javatec.pubsubemulator.spock.*")
    }
}
