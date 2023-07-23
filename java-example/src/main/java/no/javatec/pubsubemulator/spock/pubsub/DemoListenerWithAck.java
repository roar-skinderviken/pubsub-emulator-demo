package no.javatec.pubsubemulator.spock.pubsub;

import io.micronaut.context.annotation.Requires;
import io.micronaut.gcp.pubsub.annotation.MessageId;
import io.micronaut.gcp.pubsub.annotation.PubSubListener;
import io.micronaut.gcp.pubsub.annotation.Subscription;
import io.micronaut.messaging.Acknowledgement;
import no.javatec.pubsubemulator.spock.dto.SampleReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

import static no.javatec.pubsubemulator.spock.CustomEnvironment.PUBSUB_CONFIG;


@PubSubListener
@Requires(notEnv = {PUBSUB_CONFIG})
public class DemoListenerWithAck {

    private static final Logger log = LoggerFactory.getLogger(DemoListenerWithAck.class);

    private final AtomicInteger receiveCount = new AtomicInteger();

    public int getReceiveCount() {
        return receiveCount.get();
    }

    @SuppressWarnings("unused")
    @Subscription("${pubsub.subscription}")
    public void onMessageReceived(
            final SampleReturnMessage message,
            @MessageId final String messageId,
            final Acknowledgement acknowledgement) {

        receiveCount.incrementAndGet();

        log.info(getClass().getSimpleName() +
                " received message-id: " +
                messageId +
                " Message: " +
                message.returnMessage());

        acknowledgement.ack();
    }
}
