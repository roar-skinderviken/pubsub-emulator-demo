package com.example.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("gcp")
public class GcpConfigProperties {
    private String projectId;
}
