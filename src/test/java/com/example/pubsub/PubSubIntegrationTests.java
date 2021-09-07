package com.example.pubsub;

import com.example.CustomEnvironment;
import com.example.controller.SampleReturnMessage;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest(environments = CustomEnvironment.PUB_SUB_TEST)
public class PubSubIntegrationTests {

    @Inject
    DemoPublisher demoPublisher;

    @Inject
    DemoListenerWithAck demoListenerWithAck;

    @Test
    public void publishTenMessages_expectReceiveCountOfTen() throws InterruptedException {
        final int NUMBER_OF_MESSAGES_IN_TEST = 10;

        // make sure count is zero before test
        assertThat(demoListenerWithAck.getReceiveCount()).isEqualTo(0);

        // act
        IntStream.rangeClosed(1, NUMBER_OF_MESSAGES_IN_TEST)
                .forEach(it -> demoPublisher.send(new SampleReturnMessage("Hello " + it)));

        // wait a second in order to let all messages propagate through the queue
        Thread.sleep(1000);

        // assert
        assertThat(demoListenerWithAck.getReceiveCount())
                .isEqualTo(NUMBER_OF_MESSAGES_IN_TEST);
    }
}
