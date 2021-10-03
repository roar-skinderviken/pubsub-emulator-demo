package com.example.pubsub;

import com.example.CustomEnvironment;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.FixedTransportChannelProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.env.Environment;
import jakarta.inject.Singleton;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
@Factory
@Requires(env = {CustomEnvironment.CONFIG, Environment.DEVELOPMENT})
final class PubSubBeanFactory {

    private final CredentialsProvider credentialsProvider;
    private final TransportChannelProvider transportChannelProvider;
    private final ManagedChannel channel;

    PubSubBeanFactory(
            @Value("${pubsub.emulator.host}") final String emulatorHost) {

        credentialsProvider = NoCredentialsProvider.create();

        channel = ManagedChannelBuilder
                .forTarget(emulatorHost)
                .usePlaintext()
                .build();

        transportChannelProvider = FixedTransportChannelProvider
                .create(GrpcTransportChannel.create(channel));
    }

    @PreDestroy
    void preDestroy() throws InterruptedException {
        channel.shutdownNow();
        channel.awaitTermination(1, TimeUnit.SECONDS);
    }

    @Singleton
    public TopicAdminClient createTopicAdminClient() throws IOException {
        return TopicAdminClient.create(
                TopicAdminSettings.newBuilder()
                        .setTransportChannelProvider(transportChannelProvider)
                        .setCredentialsProvider(credentialsProvider).build());
    }

    @Singleton
    public SubscriptionAdminClient createSubscriptionAdminClient()
            throws IOException {

        return SubscriptionAdminClient.create(SubscriptionAdminSettings
                .newBuilder()
                .setTransportChannelProvider(transportChannelProvider)
                .setCredentialsProvider(credentialsProvider)
                .build());
    }
}