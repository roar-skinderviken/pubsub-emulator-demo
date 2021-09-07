package com.example.controller;

import com.example.service.ControllerService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import lombok.RequiredArgsConstructor;

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

