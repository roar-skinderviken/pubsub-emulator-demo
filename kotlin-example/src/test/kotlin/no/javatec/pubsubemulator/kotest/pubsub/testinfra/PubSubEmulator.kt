package no.javatec.pubsubemulator.kotest.pubsub.testinfra

import com.google.api.gax.grpc.GrpcTransportChannel
import com.google.api.gax.rpc.FixedTransportChannelProvider
import io.grpc.ManagedChannelBuilder
import org.testcontainers.containers.PubSubEmulatorContainer
import org.testcontainers.utility.DockerImageName

object PubSubEmulator {

    private lateinit var instance: PubSubEmulatorContainer
    lateinit var transportChannelProvider: FixedTransportChannelProvider

    fun start() {
        if (PubSubEmulator::instance.isInitialized) return
        instance = PubSubEmulatorContainer(
            DockerImageName.parse("gcr.io/google.com/cloudsdktool/cloud-sdk:emulators")
        )

        instance.start()
        System.setProperty("pubsub.emulator.host", instance.emulatorEndpoint)

        transportChannelProvider = FixedTransportChannelProvider.create(
            GrpcTransportChannel.create(
                ManagedChannelBuilder.forTarget(instance.emulatorEndpoint)
                    .usePlaintext()
                    .build()
            )
        )
    }

    fun stop() {
        instance.stop()
    }
}
