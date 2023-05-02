package no.javatec.pubsubemulator.kotest.pubsub.testinfra

import org.testcontainers.containers.PubSubEmulatorContainer
import org.testcontainers.utility.DockerImageName

object PubSubEmulator {

    private lateinit var instance: PubSubEmulatorContainer

    fun start() {
        if (PubSubEmulator::instance.isInitialized) return

        instance = PubSubEmulatorContainer(
            DockerImageName.parse("gcr.io/google.com/cloudsdktool/cloud-sdk:emulators")
        )

        instance.start()
        System.setProperty("pubsub.emulator.host", instance.emulatorEndpoint)
    }

    fun stop() {
        instance.stop()
    }
}
