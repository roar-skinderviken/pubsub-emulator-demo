package com.example.testframework

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