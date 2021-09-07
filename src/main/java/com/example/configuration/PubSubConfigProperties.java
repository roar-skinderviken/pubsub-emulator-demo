package com.example.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

import java.util.List;

import static java.lang.Integer.parseInt;

@Data
@ConfigurationProperties("pub-sub")
public class PubSubConfigProperties {
    private String emulatorHost;
    private String topicName;
    private List<String> subscriptionNames;

    public int getEmulatorPort() {
        final var hostNameParts = getEmulatorHost().split(":");

        return hostNameParts.length < 2
                ? 0
                : parseInt(hostNameParts[1]);
    }
}
