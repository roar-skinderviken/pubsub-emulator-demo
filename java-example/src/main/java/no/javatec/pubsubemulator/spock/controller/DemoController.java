package no.javatec.pubsubemulator.spock.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import no.javatec.pubsubemulator.spock.dto.SampleInputMessage;
import no.javatec.pubsubemulator.spock.dto.SampleReturnMessage;
import no.javatec.pubsubemulator.spock.service.ControllerService;

@ExecuteOn(TaskExecutors.IO)
@Controller("/pubsubEmulatorDemo")
public class DemoController {

    private final ControllerService demoService;

    public DemoController(ControllerService demoService) {
        this.demoService = demoService;
    }

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

