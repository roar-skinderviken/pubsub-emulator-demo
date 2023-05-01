package no.javatec.pubsubemulator.kotest.pubsub.testinfra

import com.google.api.gax.rpc.AlreadyExistsException
import com.google.cloud.pubsub.v1.SubscriptionAdminClient
import com.google.cloud.pubsub.v1.TopicAdminClient
import com.google.pubsub.v1.PushConfig
import com.google.pubsub.v1.SubscriptionName
import com.google.pubsub.v1.TopicName
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton

@Singleton
class TopicAndSubscriptionGenerator(
    private val topicAdminClient: TopicAdminClient,
    private val subscriptionAdminClient: SubscriptionAdminClient,
    @Value("\${gcp.project-id}") private val projectId: String,
    @Value("\${pubsub.topic}") private val topicName: String,
    @Value("\${pubsub.subscription}") private val subscriptionName: String
) {
    fun createTopicAndSubscriptions() {
        val topic = TopicName.of(projectId, topicName)
        createTopic(topic)
        createSubscription(topic, subscriptionName)
    }

    private fun createSubscription(
        topic: TopicName,
        subscriptionName: String
    ) {
        val subscription = SubscriptionName.of(projectId, subscriptionName)
        try {
            subscriptionAdminClient.createSubscription(
                subscription,
                topic,
                PushConfig.getDefaultInstance(),
                100
            )
        } catch (e: AlreadyExistsException) {
            // this is fine, already created
            subscriptionAdminClient.getSubscription(subscription)
        }
    }

    private fun createTopic(topic: TopicName) {
        try {
            topicAdminClient.createTopic(topic)
        } catch (e: AlreadyExistsException) {
            // this is fine, already created
            topicAdminClient.getTopic(topic)
        }
    }
}