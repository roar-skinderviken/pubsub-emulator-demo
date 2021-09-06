package com.example.http;

import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.MediaType;

@Controller("/pubsubEmulatorDemo")
public class DemoController {

    @Produces(MediaType.TEXT_PLAIN)
    @Get
    public String index() {
        return "Example Response";
    }

    @Post
    public SampleReturnMessage postMethod(@Body SampleInputMessage inputMessage){
      SampleReturnMessage retMessage = new SampleReturnMessage();
      retMessage.setReturnMessage("Hello " + inputMessage.getName() + ", thank you for sending the message");
      return retMessage;
    }
}

