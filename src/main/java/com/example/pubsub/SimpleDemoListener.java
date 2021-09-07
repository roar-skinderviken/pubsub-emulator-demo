package com.example.pubsub;

import com.example.controller.SampleReturnMessage;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.gcp.pubsub.annotation.MessageId;
import io.micronaut.gcp.pubsub.annotation.PubSubListener;
import io.micronaut.gcp.pubsub.annotation.Subscription;

@SuppressWarnings("unused")

@Requires(env = Environment.DEVELOPMENT)
@PubSubListener
public class SimpleDemoListener {

    @Subscription("${pub-sub.subscription-names[1]}")
    public void onMessageReceived(SampleReturnMessage message, @MessageId String id) {
        System.out.println(getClass().getSimpleName() +
                " received message-id: " +
                id +
                " Message: " +
                message.getReturnMessage());
    }
}
