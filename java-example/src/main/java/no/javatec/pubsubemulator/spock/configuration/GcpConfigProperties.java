package no.javatec.pubsubemulator.spock.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("gcp")
public record GcpConfigProperties(String projectId) {
}
