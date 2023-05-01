package no.javatec.pubsubemulator.kotest.dto

import io.micronaut.core.annotation.Introspected

@Introspected
data class SampleInputMessage(
    val name: String
)
