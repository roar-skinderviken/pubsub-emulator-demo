package no.javatec.pubsubemulator.kotest.spock.service;

import no.javatec.pubsubemulator.kotest.spock.dto.SampleInputMessage;
import no.javatec.pubsubemulator.kotest.spock.dto.SampleReturnMessage;
import no.javatec.pubsubemulator.kotest.spock.pubsub.DemoPublisher;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

/**
 * Service for emulating post request processing
 */
@Singleton
@RequiredArgsConstructor
public class ControllerService {

    private final DemoPublisher demoPublisher;

    public SampleReturnMessage processRequest(SampleInputMessage inputMessage) {
        final var returnMessage =
                new SampleReturnMessage("Hello " + inputMessage.getName() + ", thank you for sending the message");

        demoPublisher.send(returnMessage);
        return returnMessage;
    }
}
