package no.javatec.pubsubemulator.kotest

import io.micronaut.runtime.Micronaut.run
import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T : Any> loggerFor(): Logger = LoggerFactory.getLogger(T::class.java)

fun main(args: Array<String>) {
    run(*args)
}