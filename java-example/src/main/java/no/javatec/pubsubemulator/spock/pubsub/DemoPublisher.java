package no.javatec.pubsubemulator.spock.pubsub;

import no.javatec.pubsubemulator.spock.dto.SampleReturnMessage;
import io.micronaut.gcp.pubsub.annotation.PubSubClient;
import io.micronaut.gcp.pubsub.annotation.Topic;

@PubSubClient
public interface DemoPublisher {

    @Topic("${pubsub.topic}")
    void send(SampleReturnMessage data);
}
