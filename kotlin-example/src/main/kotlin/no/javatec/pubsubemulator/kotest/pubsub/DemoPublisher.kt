package no.javatec.pubsubemulator.kotest.pubsub

import io.micronaut.gcp.pubsub.annotation.PubSubClient
import io.micronaut.gcp.pubsub.annotation.Topic
import no.javatec.pubsubemulator.kotest.dto.SampleReturnMessage

@PubSubClient
fun interface DemoPublisher {

    @Topic("\${pubsub.topic}")
    fun send(data: SampleReturnMessage)
}