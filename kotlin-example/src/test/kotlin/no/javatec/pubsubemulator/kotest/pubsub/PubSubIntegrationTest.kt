package no.javatec.pubsubemulator.kotest.pubsub

import io.kotest.assertions.timing.eventually
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import no.javatec.pubsubemulator.kotest.dto.SampleReturnMessage
import kotlin.time.Duration.Companion.seconds

@MicronautTest
class PubSubIntegrationTest(
    demoPublisher: DemoPublisher,
    demoListenerWithAck: DemoListenerWithAck
) : BehaviorSpec({

    Given("applicationContext") {

        When("sending a pubsub message") {
            /** verify that receive count is 0 before test */
            demoListenerWithAck.receiveCount.get() shouldBe 0

            demoPublisher.send(SampleReturnMessage("Hello world"))

            Then("pubsub message should be received by listener") {
                eventually(5.seconds) {
                    demoListenerWithAck.receiveCount.get() shouldBe 1
                }
            }
        }
    }
})