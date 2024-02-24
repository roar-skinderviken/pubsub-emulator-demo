package no.javatec.pubsubemulator.kotest.pubsub

import io.micronaut.context.annotation.Requires
import io.micronaut.gcp.pubsub.annotation.PubSubListener
import io.micronaut.gcp.pubsub.annotation.Subscription
import no.javatec.pubsubemulator.kotest.CustomEnvironment.PUBSUB_CONFIG
import no.javatec.pubsubemulator.kotest.dto.SampleReturnMessage
import java.util.concurrent.atomic.AtomicInteger

@PubSubListener
@Requires(notEnv = [PUBSUB_CONFIG])
class RandomLatencyTestListener {

    /**
     * Message counter for integration tests.
     */
    val receiveCount = AtomicInteger()

    @Suppress("unused")
    @Subscription("\${pubsub.subscription}")
    fun onMessageReceived(message: SampleReturnMessage) {

        println("$message start")

        /** emulate message processing random latency */
        Thread.sleep((20..100L).random())

        println("$message end")
        receiveCount.getAndIncrement()
    }
}
