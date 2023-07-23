package no.javatec.pubsubemulator.spock.service;

import no.javatec.pubsubemulator.spock.dto.SampleInputMessage;
import no.javatec.pubsubemulator.spock.dto.SampleReturnMessage;
import no.javatec.pubsubemulator.spock.pubsub.DemoPublisher;
import jakarta.inject.Singleton;

/**
 * Service for emulating post request processing
 */
@Singleton
public class ControllerService {

    private final DemoPublisher demoPublisher;

    public ControllerService(DemoPublisher demoPublisher) {
        this.demoPublisher = demoPublisher;
    }

    public SampleReturnMessage processRequest(SampleInputMessage inputMessage) {
        final var returnMessage =
                new SampleReturnMessage("Hello " + inputMessage.name() + ", thank you for sending the message");

        demoPublisher.send(returnMessage);
        return returnMessage;
    }
}
