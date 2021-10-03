package com.example.controller

import com.example.pubsub.DemoListenerWithAck
import com.example.testframework.PubSubSpecification
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import spock.lang.Shared

class DemoControllerSpec extends PubSubSpecification {

    @Shared
    DemoListenerWithAck listener

    def initialReceiveCount = 0

    @SuppressWarnings('unused')
    def setup() {
        listener = applicationContext.getBean(DemoListenerWithAck)
        initialReceiveCount = listener.getReceiveCount()
    }

    def "when post with valid view model, data is sent to pubsub"() {
        given:
        def documentVm = new SampleInputMessage("Foo Bar")

        when:
        def response = client
                .exchange(HttpRequest.POST("/pubsubEmulatorDemo", documentVm),
                        SampleReturnMessage)

        then:
        response.status == HttpStatus.OK
        and:
        response.getBody(SampleReturnMessage)
                .get()
                .getReturnMessage()
                .startsWith("Hello Foo Bar")
        and:
        Thread.sleep(100)
        listener.getReceiveCount() == initialReceiveCount + 1
    }
}
