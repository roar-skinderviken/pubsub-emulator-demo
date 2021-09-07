package com.example.service;

import com.example.controller.SampleReturnMessage;
import jakarta.inject.Singleton;

@Singleton
public class ReceiverService {

    public void processRequest(SampleReturnMessage message) {
        // do something here, like saving entry to db
    }
}
