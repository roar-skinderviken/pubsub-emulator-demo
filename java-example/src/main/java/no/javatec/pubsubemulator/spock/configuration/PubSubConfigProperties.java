package no.javatec.pubsubemulator.spock.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;
@ConfigurationProperties("pubsub")
public record PubSubConfigProperties(String topic, String subscription) {
}
