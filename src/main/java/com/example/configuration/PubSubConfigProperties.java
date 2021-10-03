package com.example.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

import java.util.List;

@Data
@ConfigurationProperties("pubsub")
public class PubSubConfigProperties {
    private String topicName;
    private List<String> subscriptionNames;
}
