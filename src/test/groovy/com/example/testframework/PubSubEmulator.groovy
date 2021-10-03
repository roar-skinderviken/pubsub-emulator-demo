package com.example.testframework

import org.testcontainers.containers.PubSubEmulatorContainer
import org.testcontainers.utility.DockerImageName

class PubSubEmulator {
    static PubSubEmulatorContainer pubSubEmulatorContainer

    static init() {
        if (pubSubEmulatorContainer == null) {
            pubSubEmulatorContainer = new PubSubEmulatorContainer(
                    DockerImageName.parse("gcr.io/google.com/cloudsdktool/cloud-sdk:emulators"))
            pubSubEmulatorContainer.start()
        }
    }
}
