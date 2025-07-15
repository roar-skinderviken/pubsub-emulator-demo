package no.javatec.pubsubemulator.kotest.pubsub

import io.kotest.assertions.nondeterministic.eventually
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import no.javatec.pubsubemulator.kotest.dto.SampleReturnMessage
import kotlin.time.Duration.Companion.seconds

@MicronautTest
class PubSubIntegrationTest(
    demoPublisher: DemoPublisher,
    randomLatencyTestListener: RandomLatencyTestListener
) : BehaviorSpec({

    Given("applicationContext") {
        When("sending pubsub messages") {
            val initialMessageCount = randomLatencyTestListener.receiveCount.get()

            (1..NUMBER_OF_MESSAGES_IN_TEST).forEach {
                demoPublisher.send(SampleReturnMessage("Hello world $it"))
            }

            Then("pubsub messages should be received by listener") {
                eventually(10.seconds) {
                    val receivedMessageCount = randomLatencyTestListener.receiveCount.get() - initialMessageCount
                    receivedMessageCount shouldBe NUMBER_OF_MESSAGES_IN_TEST
                }
            }
        }
    }
}) {
    companion object {
        private const val NUMBER_OF_MESSAGES_IN_TEST = 5
    }
}