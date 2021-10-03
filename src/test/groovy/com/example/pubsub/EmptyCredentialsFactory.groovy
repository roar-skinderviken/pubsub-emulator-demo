package com.example.pubsub

import com.google.auth.oauth2.AccessToken
import com.google.auth.oauth2.GoogleCredentials
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

@Factory
@Requires(property = 'spec.name', value = "GoogleSpec")
class EmptyCredentialsFactory {

    @Singleton
    @Replaces(GoogleCredentials)
    GoogleCredentials mockCredentials() {
        return GoogleCredentials.create(new AccessToken("", new Date()))
    }
}