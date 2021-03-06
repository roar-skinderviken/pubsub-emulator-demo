package com.example.testframework

import io.micronaut.context.env.Environment

trait EnvironmentFixture {
    String[] getEnvironments() {
        String[] environments = [Environment.TEST]
        if (additionalEnvironments) {
            environments += additionalEnvironments
        }
        environments
    }

    String[] getAdditionalEnvironments() {
        null
    }
}