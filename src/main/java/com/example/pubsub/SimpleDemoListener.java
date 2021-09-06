package com.example.pubsub;

import com.example.http.SampleReturnMessage;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.gcp.pubsub.annotation.PubSubListener;
import io.micronaut.gcp.pubsub.annotation.Subscription;

import java.text.MessageFormat;

@SuppressWarnings("unused")

@Requires(env = Environment.DEVELOPMENT)
@PubSubListener
public class SimpleDemoListener {

    @Subscription("${pub-sub.subscription-names[1]}")
    public void onMessageReceived(SampleReturnMessage message) {
        System.out.println(MessageFormat.format("SimpleDemoListener received Message: {0}", message.getReturnMessage()));
    }
}
