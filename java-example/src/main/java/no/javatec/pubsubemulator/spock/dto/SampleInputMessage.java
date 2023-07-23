package no.javatec.pubsubemulator.spock.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record SampleInputMessage(String name) {
}
