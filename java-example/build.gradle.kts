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

micronaut {
    testRuntime("spock2")
    processing {
        annotations("no.javatec.pubsubemulator.spock.*")
    }
}
