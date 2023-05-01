package no.javatec.pubsubemulator.spock.pubsub

import no.javatec.pubsubemulator.spock.dto.SampleReturnMessage
import no.javatec.pubsubemulator.spock.fixture.PubSubSpecification
import spock.lang.Shared
import spock.util.concurrent.PollingConditions

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
        def conditions = new PollingConditions(
                timeout: NUMBER_OF_MESSAGES_IN_TEST * DELAY_IN_MILLISECONDS_PER_MSG,
                initialDelay: 0.5,
                factor: 1.0)
        and:
        demoPublisher = applicationContext.getBean(DemoPublisher)
        and:
        listener = applicationContext.getBean(DemoListenerWithAck)
        and:
        def initialReceiveCount = listener.getReceiveCount()

        when:
        IntStream.rangeClosed(1, NUMBER_OF_MESSAGES_IN_TEST)
                .forEach(it -> demoPublisher.send(
                        new SampleReturnMessage("Hello World " + it)))

        then:
        conditions.eventually {
            NUMBER_OF_MESSAGES_IN_TEST == listener.getReceiveCount() - initialReceiveCount
        }
    }
}
