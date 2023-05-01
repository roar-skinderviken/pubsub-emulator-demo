package no.javatec.pubsubemulator.kotest.spock.pubsub;

import no.javatec.pubsubemulator.kotest.spock.dto.SampleReturnMessage;
import io.micronaut.gcp.pubsub.annotation.PubSubClient;
import io.micronaut.gcp.pubsub.annotation.Topic;

@PubSubClient
public interface DemoPublisher {

    @Topic("${pubsub.topic}")
    void send(SampleReturnMessage data);
}
