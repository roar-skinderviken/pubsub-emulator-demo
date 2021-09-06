package com.example.pubsub;

import com.example.http.SampleReturnMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.pubsub.v1.PubsubMessage;
import io.micronaut.gcp.pubsub.annotation.PubSubListener;
import io.micronaut.gcp.pubsub.annotation.Subscription;
import io.micronaut.messaging.Acknowledgement;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")

@PubSubListener
public class DemoListenerWithAck {

    private final ObjectReader OBJECT_READER = new ObjectMapper().readerFor(SampleReturnMessage.class);

    private final AtomicInteger receiveCount = new AtomicInteger();

    public int getReceiveCount() {
        return receiveCount.get();
    }

    @Subscription("${pub-sub.subscription-names[0]}")
    public void onMessageReceived(PubsubMessage message, Acknowledgement acknowledgement) throws JsonProcessingException {
        receiveCount.incrementAndGet();

        SampleReturnMessage sampleReturnMessage = OBJECT_READER.readValue(message.getData().toStringUtf8());

        System.out.println("DemoListenerWithAck received ID: " +
                message.getMessageId() +
                " Message: " +
                sampleReturnMessage.getReturnMessage());

        acknowledgement.ack();
    }
}
