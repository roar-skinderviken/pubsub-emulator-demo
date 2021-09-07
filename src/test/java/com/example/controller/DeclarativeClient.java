package com.example.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client("/pubsubEmulatorDemo")
public interface DeclarativeClient {

    @Get(consumes = MediaType.TEXT_PLAIN, produces = MediaType.TEXT_PLAIN)
    String getExampleText();

    @Post(consumes = MediaType.APPLICATION_JSON)
    SampleReturnMessage postSampleInput(@Body SampleInputMessage message);
}
