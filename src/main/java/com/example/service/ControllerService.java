package com.example.service;

import com.example.controller.SampleInputMessage;
import com.example.controller.SampleReturnMessage;
import com.example.pubsub.DemoPublisher;
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
