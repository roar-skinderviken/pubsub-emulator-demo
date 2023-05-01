package no.javatec.pubsubemulator.kotest.spock.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import lombok.RequiredArgsConstructor;
import no.javatec.pubsubemulator.kotest.spock.dto.SampleInputMessage;
import no.javatec.pubsubemulator.kotest.spock.dto.SampleReturnMessage;
import no.javatec.pubsubemulator.kotest.spock.service.ControllerService;

@Controller("/pubsubEmulatorDemo")
@RequiredArgsConstructor
public class DemoController {

    private final ControllerService demoService;

    @Produces(MediaType.TEXT_PLAIN)
    @Get
    public String index() {
        return "Example Response";
    }

    @Post
    public SampleReturnMessage postMethod(@Body SampleInputMessage inputMessage) {
        return demoService.processRequest(inputMessage);
    }
}

