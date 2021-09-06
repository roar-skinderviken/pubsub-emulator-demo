package com.example.pubsub;

import com.example.CustomEnvironment;
import com.example.configuration.GcpConfigProperties;
import com.example.configuration.PubSubConfigProperties;
import com.example.pubsub.TopicAndSubscriptionGenerator;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Requires;
import lombok.RequiredArgsConstructor;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Class for running pubsub emulator. {@code @Context} annotation is used in order to load bean eagerly.
 * Created for pubsub tests only.
 */
@Context
@Requires(env = CustomEnvironment.PUB_SUB_TEST)
@RequiredArgsConstructor
public class PubSubTestContext {

    private final TopicAndSubscriptionGenerator topicAndSubscriptionGenerator;
    private final PubSubConfigProperties pubSubConfigProperties;
    private final GcpConfigProperties gcpConfigProperties;

    private GenericContainer pubsubContainer;

    @PostConstruct
    void createResources() {

        int pubsubPort = pubSubConfigProperties.getEmulatorPort();

        pubsubContainer =
                new FixedHostPortGenericContainer("google/cloud-sdk:latest")
                        .withFixedExposedPort(pubsubPort, pubsubPort)
                        .withExposedPorts(pubsubPort)
                        .withCommand(
                                "/bin/sh",
                                "-c",
                                String.format(
                                        "gcloud beta emulators pubsub start --project %s --host-port=0.0.0.0:%d",
                                        gcpConfigProperties.getProjectId(), pubsubPort)
                        )
                        .waitingFor(new LogMessageWaitStrategy().withRegEx("(?s).*started.*$"));

        pubsubContainer.start();
        topicAndSubscriptionGenerator.createTopicAndSubscriptions();
    }

    @PreDestroy
    void destroyResources() {
        pubsubContainer.stop();
    }
}
