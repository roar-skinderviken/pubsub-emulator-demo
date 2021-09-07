package com.example.pubsub;

import com.example.controller.SampleReturnMessage;
import com.example.service.ReceiverService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.pubsub.v1.PubsubMessage;
import io.micronaut.gcp.pubsub.annotation.PubSubListener;
import io.micronaut.gcp.pubsub.annotation.Subscription;
import io.micronaut.messaging.Acknowledgement;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")

@PubSubListener
@RequiredArgsConstructor
public class DemoListenerWithAck {

    private final ReceiverService receiverService;

    private final ObjectReader OBJECT_READER = new ObjectMapper().readerFor(SampleReturnMessage.class);
    private final AtomicInteger receiveCount = new AtomicInteger();

    @PostConstruct
    private void postConstructor() {
        System.out.println(getClass().getSimpleName() + " constructed");
    }

    public int getReceiveCount() {
        return receiveCount.get();
    }

    @Subscription("${pub-sub.subscription-names[0]}")
    public void onMessageReceived(PubsubMessage message, Acknowledgement acknowledgement) throws JsonProcessingException {
        receiveCount.incrementAndGet();

        SampleReturnMessage sampleReturnMessage = OBJECT_READER.readValue(message.getData().toStringUtf8());

        System.out.println(getClass().getSimpleName() +
                " received message-id: " +
                message.getMessageId() +
                " Message: " +
                sampleReturnMessage.getReturnMessage());

        // do something with the payload
        receiverService.processRequest(sampleReturnMessage);

        acknowledgement.ack();
    }
}
