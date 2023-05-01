package no.javatec.pubsubemulator.kotest.dto

import io.micronaut.core.annotation.Introspected

@Introspected
data class SampleReturnMessage(
    val returnMessage: String
)
