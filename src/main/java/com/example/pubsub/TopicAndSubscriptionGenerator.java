package com.example.pubsub;

import com.example.configuration.GcpConfigProperties;
import com.example.configuration.PubSubConfigProperties;
import com.google.api.gax.rpc.AlreadyExistsException;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.TopicName;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class TopicAndSubscriptionGenerator {

    private final TopicAdminClient topicAdminClient;
    private final SubscriptionAdminClient subscriptionAdminClient;
    private final GcpConfigProperties gcpConfigProperties;
    private final PubSubConfigProperties pubSubConfigProperties;

    public void createTopicAndSubscriptions() {
        createTopic();
        pubSubConfigProperties.getSubscriptionNames().forEach(this::createSubscription);
    }

    private void createSubscription(String subscriptionName) {
        var topic = TopicName.of(gcpConfigProperties.getProjectId(), pubSubConfigProperties.getTopicName());
        var subscription = ProjectSubscriptionName.of(gcpConfigProperties.getProjectId(), subscriptionName);

        try {
            subscriptionAdminClient
                    .createSubscription(subscription, topic, PushConfig.getDefaultInstance(), 100);
        } catch (AlreadyExistsException e) {
            // this is fine, already created
            subscriptionAdminClient.getSubscription(subscription);
        }
    }

    private void createTopic() {
        var topic = TopicName.of(gcpConfigProperties.getProjectId(), pubSubConfigProperties.getTopicName());
        try {
            topicAdminClient.createTopic(topic);
        } catch (AlreadyExistsException e) {
            // this is fine, already created
            topicAdminClient.getTopic(topic);
        }
    }
}
