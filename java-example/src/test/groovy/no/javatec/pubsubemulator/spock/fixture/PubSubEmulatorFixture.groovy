package no.javatec.pubsubemulator.spock.fixture

import no.javatec.pubsubemulator.spock.pubsub.testinfra.PubSubEmulator

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