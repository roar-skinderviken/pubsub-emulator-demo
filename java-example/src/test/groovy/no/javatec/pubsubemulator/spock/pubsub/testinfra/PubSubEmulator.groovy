package no.javatec.pubsubemulator.spock.pubsub.testinfra

import com.google.api.gax.grpc.GrpcTransportChannel
import com.google.api.gax.rpc.FixedTransportChannelProvider
import com.google.api.gax.rpc.TransportChannelProvider
import io.grpc.ManagedChannelBuilder
import org.testcontainers.containers.PubSubEmulatorContainer
import org.testcontainers.utility.DockerImageName

class PubSubEmulator {
    static PubSubEmulatorContainer pubSubEmulatorContainer
    static TransportChannelProvider transportChannelProvider

    static init() {
        if (pubSubEmulatorContainer == null) {
            pubSubEmulatorContainer = new PubSubEmulatorContainer(
                    DockerImageName.parse("gcr.io/google.com/cloudsdktool/cloud-sdk:emulators"))

            pubSubEmulatorContainer.start()

            transportChannelProvider = FixedTransportChannelProvider.create(
                    GrpcTransportChannel.create(
                            ManagedChannelBuilder.forTarget(pubSubEmulatorContainer.emulatorEndpoint)
                                    .usePlaintext()
                                    .build()
                    )
            )
        }
    }
}
