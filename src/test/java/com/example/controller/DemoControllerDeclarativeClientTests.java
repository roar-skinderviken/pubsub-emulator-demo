package com.example.controller;

import com.example.CustomEnvironment;
import com.example.pubsub.DemoListenerWithAck;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Controller tests using declarative http client
 */
@MicronautTest(environments = CustomEnvironment.PUB_SUB_TEST)
public class DemoControllerDeclarativeClientTests {

    @Inject
    DeclarativeClient declarativeClient;

    @Inject
    DemoListenerWithAck demoListenerWithAck;

    @Test
    public void getExampleText_normalConditions_expectResult() {

        // act + assert
        assertThat(declarativeClient.getExampleText())
                .as("Check return message from GET")
                .isEqualTo("Example Response");
    }

    @Test
    public void postSampleInput_validModel_expectResult() {

        // arrange
        var inputMessage = new SampleInputMessage("Test Name");

        // act
        var result = declarativeClient.postSampleInput(inputMessage);

        // assert
        assertThat(result.getReturnMessage())
                .as("Check return message from POST")
                .isEqualTo("Hello " + inputMessage.getName() + ", thank you for sending the message");
    }

    @Test
    public void postSampleInput_validModel_expectMessageToBePublished() throws InterruptedException {

        // make sure count is zero before test
        assertThat(demoListenerWithAck.getReceiveCount()).isEqualTo(0);

        // act
        declarativeClient.postSampleInput(new SampleInputMessage("Test Name"));

        // wait a tiny amount of time to let message propagate through the queue
        Thread.sleep(100);

        // assert
        assertThat(demoListenerWithAck.getReceiveCount()).isEqualTo(1);
    }
}
