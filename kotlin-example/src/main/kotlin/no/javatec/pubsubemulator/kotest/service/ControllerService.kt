package no.javatec.pubsubemulator.kotest.service

import jakarta.inject.Singleton
import no.javatec.pubsubemulator.kotest.dto.SampleInputMessage
import no.javatec.pubsubemulator.kotest.dto.SampleReturnMessage
import no.javatec.pubsubemulator.kotest.pubsub.DemoPublisher

@Singleton
class ControllerService(private val demoPublisher: DemoPublisher) {

    fun processRequest(inputMessage: SampleInputMessage): SampleReturnMessage {
        val returnMessage =
            SampleReturnMessage("Hello ${inputMessage.name}, thank you for sending the message")

        demoPublisher.send(returnMessage)
        return returnMessage
    }
}