package com.example.pubsub

import com.example.controller.SampleReturnMessage
import com.example.testframework.PubSubSpecification
import spock.lang.Shared

import java.util.stream.IntStream

class PubSubIntegrationSpec extends PubSubSpecification {

    def NUMBER_OF_MESSAGES_IN_TEST = 5
    def DELAY_IN_MILLISECONDS_PER_MSG = 100

    @Shared
    DemoPublisher demoPublisher

    @Shared
    DemoListenerWithAck listener

    def "when a number of messages is sent, same amount of messages are received"() {
        given:
        demoPublisher = applicationContext.getBean(DemoPublisher)
        listener = applicationContext.getBean(DemoListenerWithAck)
        def initialReceiveCount = listener.getReceiveCount()

        when:
        IntStream.rangeClosed(1, NUMBER_OF_MESSAGES_IN_TEST)
                .forEach(it -> demoPublisher.send(
                        new SampleReturnMessage("Hello World " + it)))

        // wait a bit in order to let all messages propagate through the queue
        Thread.sleep(NUMBER_OF_MESSAGES_IN_TEST * DELAY_IN_MILLISECONDS_PER_MSG)

        then:
        NUMBER_OF_MESSAGES_IN_TEST == listener.getReceiveCount() - initialReceiveCount
    }
}
