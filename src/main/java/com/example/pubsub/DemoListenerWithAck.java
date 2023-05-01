package com.example.pubsub;

import com.example.controller.SampleReturnMessage;
import io.micronaut.gcp.pubsub.annotation.MessageId;
import io.micronaut.gcp.pubsub.annotation.PubSubListener;
import io.micronaut.gcp.pubsub.annotation.Subscription;
import io.micronaut.messaging.Acknowledgement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
@Slf4j
@PubSubListener
@RequiredArgsConstructor
public class DemoListenerWithAck {

    private final AtomicInteger receiveCount = new AtomicInteger();

    public int getReceiveCount() {
        return receiveCount.get();
    }

    @Subscription("${pubsub.subscription-names[0]}")
    public void onMessageReceived(
            final SampleReturnMessage message,
            @MessageId final String messageId,
            final Acknowledgement acknowledgement) {

        receiveCount.incrementAndGet();

        log.info(getClass().getSimpleName() +
                " received message-id: " +
                messageId +
                " Message: " +
                message.getReturnMessage());

        acknowledgement.ack();
    }
}
