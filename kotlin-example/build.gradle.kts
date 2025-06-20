application {
    mainClass = "no.javatec.pubsubemulator.kotest.ApplicationKt"
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    runtimeOnly("org.yaml:snakeyaml")
    runtimeOnly("ch.qos.logback:logback-classic")

    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut.gcp:micronaut-gcp-pubsub")

    testImplementation("io.micronaut.test:micronaut-test-kotest5")
    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation(libs.testcontainers.gcloud)
}

micronaut {
    testRuntime("kotest5")
    processing {
        annotations("no.javatec.pubsubemulator.kotest.*")
    }
}

tasks.test {
    jvmArgs(
        "-Xshare:off",
        "-XX:+EnableDynamicAgentLoading",
        "-Dkotest.framework.classpath.scanning.autoscan.disable=true",
        "-Dkotest.framework.config.fqn=no.javatec.pubsubemulator.kotest.KotestConfig"
    )
    useJUnitPlatform()
}
