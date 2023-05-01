package no.javatec.pubsubemulator.spock.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("gcp")
public class GcpConfigProperties {
    private String projectId;
}
