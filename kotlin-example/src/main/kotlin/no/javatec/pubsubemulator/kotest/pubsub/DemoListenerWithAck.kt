package no.javatec.pubsubemulator.kotest.pubsub

import io.micronaut.gcp.pubsub.annotation.MessageId
import io.micronaut.gcp.pubsub.annotation.PubSubListener
import io.micronaut.gcp.pubsub.annotation.Subscription
import io.micronaut.messaging.Acknowledgement
import no.javatec.pubsubemulator.kotest.dto.SampleReturnMessage
import no.javatec.pubsubemulator.kotest.loggerFor
import java.util.concurrent.atomic.AtomicInteger

@PubSubListener
class DemoListenerWithAck {

    private val log = loggerFor<DemoListenerWithAck>()
    val receiveCount: AtomicInteger = AtomicInteger(0)

    @Suppress("unused")
    @Subscription("\${pubsub.subscription}")
    fun onMessageReceived(
        message: SampleReturnMessage,
        @MessageId messageId: String,
        acknowledgement: Acknowledgement
    ) {
        receiveCount.incrementAndGet()

        log.info(
            javaClass.simpleName +
                    " received message-id: " +
                    messageId +
                    " Message: " +
                    message.returnMessage
        )

        acknowledgement.ack()
    }
}