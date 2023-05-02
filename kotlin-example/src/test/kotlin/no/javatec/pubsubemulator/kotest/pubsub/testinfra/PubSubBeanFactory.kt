package no.javatec.pubsubemulator.kotest.pubsub.testinfra

import com.google.api.gax.core.NoCredentialsProvider
import com.google.api.gax.grpc.GrpcTransportChannel
import com.google.api.gax.rpc.FixedTransportChannelProvider
import com.google.api.gax.rpc.TransportChannelProvider
import com.google.cloud.pubsub.v1.SubscriptionAdminClient
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings
import com.google.cloud.pubsub.v1.TopicAdminClient
import com.google.cloud.pubsub.v1.TopicAdminSettings
import io.grpc.ManagedChannelBuilder
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.context.annotation.Value
import jakarta.inject.Named
import jakarta.inject.Singleton
import no.javatec.pubsubemulator.kotest.CustomEnvironment.PUBSUB_CONFIG
import java.io.IOException

@Factory
class PubSubBeanFactory {

    private val credentialsProvider = NoCredentialsProvider.create()

    @Singleton
    @Named("pubsub")
    @Replaces(TransportChannelProvider::class)
    fun localChannelProvider(
        @Value("\${pubsub.emulator.host}") emulatorEndpoint: String
    ): TransportChannelProvider = FixedTransportChannelProvider.create(
        GrpcTransportChannel.create(
            ManagedChannelBuilder.forTarget(emulatorEndpoint)
                .usePlaintext()
                .build()
        )
    )

    @Requires(env = [PUBSUB_CONFIG])
    @Singleton
    @Throws(IOException::class)
    fun createTopicAdminClient(
        transportChannelProvider: TransportChannelProvider
    ): TopicAdminClient = TopicAdminClient.create(
        TopicAdminSettings
            .newBuilder()
            .setTransportChannelProvider(transportChannelProvider)
            .setCredentialsProvider(credentialsProvider)
            .build()
    )

    @Requires(env = [PUBSUB_CONFIG])
    @Singleton
    @Throws(IOException::class)
    fun createSubscriptionAdminClient(
        transportChannelProvider: TransportChannelProvider
    ): SubscriptionAdminClient = SubscriptionAdminClient.create(
        SubscriptionAdminSettings
            .newBuilder()
            .setTransportChannelProvider(transportChannelProvider)
            .setCredentialsProvider(credentialsProvider)
            .build()
    )
}