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

            System.setProperty("pubsub.emulator.host", pubSubEmulatorContainer.emulatorEndpoint)

            /** In order to avoid the below error, we'll have to create FixedTransportChannelProvider after
             * test container is started.
             *
             * io.grpc.internal.ManagedChannelOrphanWrapper$ManagedChannelReference cleanQueue
             * SEVERE: *~*~*~ Previous channel ManagedChannelImpl{logId=5, target=localhost:33090} was not shutdown properly!!! ~*~*~*
             * Make sure to call shutdown()/shutdownNow() and wait until awaitTermination() returns true.
             */
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
