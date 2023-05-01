package no.javatec.pubsubemulator.kotest.pubsub.testinfra

import com.google.api.gax.core.NoCredentialsProvider
import com.google.api.gax.rpc.TransportChannelProvider
import com.google.cloud.pubsub.v1.SubscriptionAdminClient
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings
import com.google.cloud.pubsub.v1.TopicAdminClient
import com.google.cloud.pubsub.v1.TopicAdminSettings
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import jakarta.inject.Named
import jakarta.inject.Singleton
import java.io.IOException

@Factory
class PubSubBeanFactory {

    private val credentialsProvider = NoCredentialsProvider.create()

    @Singleton
    @Named("pubsub")
    @Replaces(TransportChannelProvider::class)
    fun localChannelProvider(): TransportChannelProvider = PubSubEmulator.transportChannelProvider

    @Singleton
    @Throws(IOException::class)
    fun createTopicAdminClient(
        transportChannelProvider: TransportChannelProvider?
    ): TopicAdminClient? {
        return TopicAdminClient.create(
            TopicAdminSettings
                .newBuilder()
                .setTransportChannelProvider(transportChannelProvider)
                .setCredentialsProvider(credentialsProvider)
                .build()
        )
    }

    @Singleton
    @Throws(IOException::class)
    fun createSubscriptionAdminClient(
        transportChannelProvider: TransportChannelProvider?
    ): SubscriptionAdminClient? {
        return SubscriptionAdminClient.create(
            SubscriptionAdminSettings
                .newBuilder()
                .setTransportChannelProvider(transportChannelProvider)
                .setCredentialsProvider(credentialsProvider)
                .build()
        )
    }
}