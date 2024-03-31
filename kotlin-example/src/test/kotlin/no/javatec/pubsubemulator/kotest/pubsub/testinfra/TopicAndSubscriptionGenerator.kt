package no.javatec.pubsubemulator.kotest.pubsub.testinfra

import com.google.api.gax.rpc.AlreadyExistsException
import com.google.cloud.pubsub.v1.SubscriptionAdminClient
import com.google.cloud.pubsub.v1.TopicAdminClient
import com.google.pubsub.v1.Subscription
import com.google.pubsub.v1.SubscriptionName
import com.google.pubsub.v1.TopicName
import io.micronaut.context.annotation.Requires
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import no.javatec.pubsubemulator.kotest.CustomEnvironment.PUBSUB_CONFIG

@Singleton
@Requires(env = [PUBSUB_CONFIG])
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
            subscriptionAdminClient.createSubscription(Subscription.newBuilder()
                .setName(subscription.toString())
                .setTopic(topic.toString())
                .setEnableMessageOrdering(true)
                .setAckDeadlineSeconds(100)
                .build()
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