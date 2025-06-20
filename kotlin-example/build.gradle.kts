application {
    mainClass = "no.javatec.pubsubemulator.kotest.ApplicationKt"
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    testImplementation("io.micronaut.test:micronaut-test-kotest5")
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
