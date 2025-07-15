package no.javatec.pubsubemulator.kotest

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.micronaut.context.ApplicationContext
import io.micronaut.test.extensions.kotest5.MicronautKotest5Extension
import no.javatec.pubsubemulator.kotest.CustomEnvironment.PUBSUB_CONFIG
import no.javatec.pubsubemulator.kotest.pubsub.testinfra.PubSubEmulator
import no.javatec.pubsubemulator.kotest.pubsub.testinfra.TopicAndSubscriptionGenerator


@Suppress("unused")
object KotestConfig : AbstractProjectConfig() {
    override fun extensions(): List<Extension> = listOf(MicronautKotest5Extension)

    private val log = loggerFor<KotestConfig>()

    override suspend fun beforeProject() {
        PubSubEmulator.start()
        setupPubSubTopicsAndSubscriptions()
        log.info("PubSubEmulator started and configured")
    }

    override suspend fun afterProject() {
        PubSubEmulator.stop()
        log.info("PubSubEmulator stopped")
    }

    private fun setupPubSubTopicsAndSubscriptions() {
        val tempApplicationContext: ApplicationContext = ApplicationContext.run(PUBSUB_CONFIG)
        tempApplicationContext.getBean(TopicAndSubscriptionGenerator::class.java)
            .run { createTopicAndSubscriptions() }

        tempApplicationContext.stop()
    }
}