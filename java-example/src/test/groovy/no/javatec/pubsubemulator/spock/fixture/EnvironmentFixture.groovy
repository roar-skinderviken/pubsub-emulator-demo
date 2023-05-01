package no.javatec.pubsubemulator.spock.fixture

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