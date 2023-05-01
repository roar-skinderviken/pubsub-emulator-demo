package no.javatec.pubsubemulator.kotest.spock.testframework

import no.javatec.pubsubemulator.kotest.spock.pubsub.testinfra.PubSubEmulator

trait PubSubEmulatorFixture {
    Map<String, Object> getPubSubConfiguration() {
        if (PubSubEmulator.pubSubEmulatorContainer == null || !PubSubEmulator.pubSubEmulatorContainer.isRunning()) {
            PubSubEmulator.init()
        }
        [
                "pubsub.emulator-host": PubSubEmulator.pubSubEmulatorContainer.getEmulatorEndpoint()
        ]
    }
}