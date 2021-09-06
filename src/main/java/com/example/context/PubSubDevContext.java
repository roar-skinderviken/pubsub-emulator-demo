package com.example.context;

import com.example.pubsub.TopicAndSubscriptionGenerator;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;

@Context
@Requires(env = {Environment.DEVELOPMENT})
@RequiredArgsConstructor
public class PubSubDevContext {

    private final TopicAndSubscriptionGenerator topicAndSubscriptionGenerator;

    @PostConstruct
    void createResources() {
        topicAndSubscriptionGenerator.createTopicAndSubscriptions();
    }
}
