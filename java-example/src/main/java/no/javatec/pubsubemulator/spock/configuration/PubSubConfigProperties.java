package no.javatec.pubsubemulator.spock.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("pubsub")
public class PubSubConfigProperties {
    private String topic;
    private String subscription;
}
