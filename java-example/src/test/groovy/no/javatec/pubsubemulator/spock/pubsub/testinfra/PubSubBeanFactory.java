package no.javatec.pubsubemulator.spock.pubsub.testinfra;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminSettings;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.io.IOException;

import static no.javatec.pubsubemulator.spock.CustomEnvironment.PUBSUB_CONFIG;

@Factory
final class PubSubBeanFactory {

    private final CredentialsProvider credentialsProvider;

    public PubSubBeanFactory() {
        this.credentialsProvider = NoCredentialsProvider.create();
    }

    @Singleton
    @Named("pubsub")
    @Replaces(TransportChannelProvider.class)
    public TransportChannelProvider localChannelProvider() {
        return PubSubEmulator.getTransportChannelProvider();
    }

    @Requires(env = {PUBSUB_CONFIG})
    @Singleton
    public TopicAdminClient createTopicAdminClient(
            TransportChannelProvider transportChannelProvider) throws IOException {
        return TopicAdminClient.create(
                TopicAdminSettings
                        .newBuilder()
                        .setTransportChannelProvider(transportChannelProvider)
                        .setCredentialsProvider(credentialsProvider)
                        .build());
    }

    @Requires(env = {PUBSUB_CONFIG})
    @Singleton
    public SubscriptionAdminClient createSubscriptionAdminClient(
            TransportChannelProvider transportChannelProvider) throws IOException {

        return SubscriptionAdminClient.create(SubscriptionAdminSettings
                .newBuilder()
                .setTransportChannelProvider(transportChannelProvider)
                .setCredentialsProvider(credentialsProvider)
                .build());
    }
}