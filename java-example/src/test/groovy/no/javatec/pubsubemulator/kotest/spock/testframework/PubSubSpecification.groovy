package no.javatec.pubsubemulator.kotest.spock.testframework


import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import no.javatec.pubsubemulator.kotest.spock.CustomEnvironment
import no.javatec.pubsubemulator.kotest.spock.pubsub.testinfra.TopicAndSubscriptionGenerator
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

abstract class PubSubSpecification extends Specification
        implements PubSubEmulatorFixture, EnvironmentFixture {

    @AutoCleanup
    @Shared
    EmbeddedServer embeddedServer

    @AutoCleanup
    @Shared
    ApplicationContext applicationContext

    @AutoCleanup
    @Shared
    HttpClient httpClient

    BlockingHttpClient getClient() {
        httpClient.toBlocking()
    }

    @SuppressWarnings('unused')
    def setupSpec() {

        // start the pubsub emulator
        def emulatorHost = "dns:///" + getPubSubConfiguration().get("pubsub.emulator-host")

        // start a temporary applicationContext in order to read config
        applicationContext = ApplicationContext.run(
                ["pubsub.emulator.host": emulatorHost], CustomEnvironment.PUBSUB_CONFIG)

        def topicAndSubscriptionGenerator
                = applicationContext.getBean(TopicAndSubscriptionGenerator)
        topicAndSubscriptionGenerator.createTopicAndSubscriptions()

        // stop the temporary applicationContext
        applicationContext.stop()

        // start the actual applicationContext
        embeddedServer = ApplicationContext.run(
                EmbeddedServer,
                [
                        "spec.name"           : "PubSubEmulatorSpec",
                        "pubsub.emulator.host": emulatorHost
                ],
                environments) as EmbeddedServer

        applicationContext = embeddedServer.applicationContext
        httpClient = applicationContext.createBean(HttpClient, embeddedServer.URL)
    }
}