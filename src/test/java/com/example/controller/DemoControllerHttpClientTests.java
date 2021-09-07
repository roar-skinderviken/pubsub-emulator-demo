package com.example.controller;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Controller tests using {@code HttpClient}
 */
@MicronautTest
public class DemoControllerHttpClientTests {

    @Inject
    @Client("/pubsubEmulatorDemo")
    HttpClient httpClient;

    @Test
    public void getExampleText_normalConditions_expectResult() {

        // act
        String result = httpClient.toBlocking().retrieve(HttpRequest.GET("/"), String.class);

        // assert
        assertThat(result)
                .as("Check return message from GET")
                .isEqualTo("Example Response"); // assertj
    }
}
