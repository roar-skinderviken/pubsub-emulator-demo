package no.javatec.pubsubemulator.kotest.pubsub

import io.micronaut.gcp.pubsub.annotation.OrderingKey
import io.micronaut.gcp.pubsub.annotation.PubSubClient
import io.micronaut.gcp.pubsub.annotation.Topic
import no.javatec.pubsubemulator.kotest.dto.SampleReturnMessage

@PubSubClient
fun interface DemoPublisherWithMessageKey {

    @Topic("\${pubsub.topic}")
    fun send(
        message: SampleReturnMessage,
        @OrderingKey key: String
    )
}
