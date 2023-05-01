package no.javatec.pubsubemulator.kotest.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import no.javatec.pubsubemulator.kotest.dto.SampleInputMessage
import no.javatec.pubsubemulator.kotest.service.ControllerService

@Controller("/pubsubEmulatorDemo")
class DemoController(private val demoService: ControllerService) {

    @Produces(MediaType.TEXT_PLAIN)
    @Get
    fun index(): String = "Example Response"

    @Post
    fun postMethod(@Body inputMessage: SampleInputMessage) = demoService.processRequest(inputMessage)
}