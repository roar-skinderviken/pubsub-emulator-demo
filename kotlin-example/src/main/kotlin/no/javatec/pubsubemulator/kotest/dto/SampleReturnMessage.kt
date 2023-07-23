package no.javatec.pubsubemulator.kotest.dto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class SampleReturnMessage(
    val returnMessage: String
)
