package com.example.pubsub;

import com.example.configuration.PubSubConfigProperties;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.FixedTransportChannelProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import io.grpc.ManagedChannelBuilder;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Factory
@RequiredArgsConstructor
public class PubSubBeanFactory {

    private final PubSubConfigProperties pubSubConfigProperties;

    private CredentialsProvider credentialsProvider;
    private TransportChannelProvider transportChannelProvider;

    @PostConstruct
    void postConstruct() {
        credentialsProvider = NoCredentialsProvider.create();

        var channel = ManagedChannelBuilder
                .forTarget(pubSubConfigProperties.getEmulatorHost()).usePlaintext().build();

        transportChannelProvider = FixedTransportChannelProvider.create(GrpcTransportChannel.create(channel));
    }

    @Singleton
    public TopicAdminClient createTopicAdminClient() throws IOException {
        return TopicAdminClient.create(
                TopicAdminSettings.newBuilder()
                        .setTransportChannelProvider(transportChannelProvider)
                        .setCredentialsProvider(credentialsProvider).build());
    }

    @Singleton
    public SubscriptionAdminClient createSubscriptionAdminClient() throws IOException {
        return SubscriptionAdminClient.create(SubscriptionAdminSettings.newBuilder()
                .setTransportChannelProvider(transportChannelProvider)
                .setCredentialsProvider(credentialsProvider)
                .build());
    }
}
