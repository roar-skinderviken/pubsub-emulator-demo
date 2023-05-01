package no.javatec.pubsubemulator.spock.pubsub.testinfra;

import io.micronaut.context.annotation.Requires;
import no.javatec.pubsubemulator.spock.configuration.GcpConfigProperties;
import no.javatec.pubsubemulator.spock.configuration.PubSubConfigProperties;
import com.google.api.gax.rpc.AlreadyExistsException;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.SubscriptionName;
import com.google.pubsub.v1.TopicName;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import static no.javatec.pubsubemulator.spock.CustomEnvironment.PUBSUB_CONFIG;

@Singleton
@RequiredArgsConstructor
@Requires(env = {PUBSUB_CONFIG})
public class TopicAndSubscriptionGenerator {

    private final TopicAdminClient topicAdminClient;
    private final SubscriptionAdminClient subscriptionAdminClient;
    private final GcpConfigProperties gcpConfigProperties;
    private final PubSubConfigProperties pubSubConfigProperties;

    public void createTopicAndSubscriptions() {
        final var topic =
                TopicName.of(gcpConfigProperties.getProjectId(), pubSubConfigProperties.getTopic());

        createTopic(topic);
        createSubscription(topic, pubSubConfigProperties.getSubscription());
    }

    private void createSubscription(TopicName topic, String subscriptionName) {
        final var subscription =
                SubscriptionName.of(gcpConfigProperties.getProjectId(), subscriptionName);

        try {
            subscriptionAdminClient.createSubscription(
                    subscription,
                    topic,
                    PushConfig.getDefaultInstance(),
                    100);
        } catch (AlreadyExistsException e) {
            // this is fine, already created
            subscriptionAdminClient.getSubscription(subscription);
        }
    }

    private void createTopic(TopicName topic) {
        try {
            topicAdminClient.createTopic(topic);
        } catch (AlreadyExistsException e) {
            // this is fine, already created
            topicAdminClient.getTopic(topic);
        }
    }
}
