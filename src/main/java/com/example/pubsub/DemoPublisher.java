package com.example.pubsub;

import com.example.controller.SampleReturnMessage;
import io.micronaut.gcp.pubsub.annotation.PubSubClient;
import io.micronaut.gcp.pubsub.annotation.Topic;

@SuppressWarnings("unused")
@PubSubClient
public interface DemoPublisher {

    @Topic("${pubsub.topic-name}")
    void send(SampleReturnMessage data);
}
