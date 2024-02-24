package no.javatec.pubsubemulator.kotest.pubsub

import io.kotest.assertions.nondeterministic.eventually
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import kotlinx.coroutines.delay
import no.javatec.pubsubemulator.kotest.dto.SampleReturnMessage
import no.javatec.pubsubemulator.kotest.pubsub.PubSubTestUtils.tapSystemOut
import kotlin.time.Duration.Companion.seconds

@MicronautTest
class MessageConcurrencyTest(
    demoPublisherWithMessageKey: DemoPublisherWithMessageKey,
    demoPublisherWithoutMessageKey: DemoPublisher,
    randomLatencyTestListener: RandomLatencyTestListener
) : BehaviorSpec({

    /**
     * These tests are showing the difference in message ordering between sending pubsub messages
     * without and then with message key. When processing same entity, it is crucial that pubsub
     * messages arrives in order.
     *
     * Tests are using a pubsub listener that emulates random latency in range 20-100 ms.
     */
    Given("test listener with random latency") {
        var initialMessageCount = 0

        beforeContainer {
            initialMessageCount = randomLatencyTestListener.receiveCount.get()
        }

        When("sending multiple messages without message key") {
            val output = tapSystemOut {
                (1..NUMBER_OF_MESSAGES_IN_TEST).forEach {
                    demoPublisherWithoutMessageKey.send(
                        SampleReturnMessage("Message $it")
                    )
                }

                eventually(10.seconds) {
                    randomLatencyTestListener.receiveCount.get() - initialMessageCount shouldBe
                            NUMBER_OF_MESSAGES_IN_TEST
                }
            }

            Then("messages are processed out of order") {
                output shouldNotBe expectedOutput
            }
        }

        When("sending multiple messages with identical message key") {
            val output = tapSystemOut {
                (1..NUMBER_OF_MESSAGES_IN_TEST).forEach {
                    demoPublisherWithMessageKey.send(
                        SampleReturnMessage("Message $it"), MESSAGE_KEY
                    )

                    /** emulate latency in an imaginary preceding component */
                    delay((20L..100L).random())
                }

                eventually(10.seconds) {
                    randomLatencyTestListener.receiveCount.get() - initialMessageCount shouldBe
                            NUMBER_OF_MESSAGES_IN_TEST
                }
            }

            Then("messages are processed in order") {
                output shouldBe expectedOutput
            }
        }
    }
}) {
    companion object {

        private const val MESSAGE_KEY = "42"
        private const val NUMBER_OF_MESSAGES_IN_TEST = 10

        private val expectedOutput =
            (1..NUMBER_OF_MESSAGES_IN_TEST)
                .fold("") { acc, i ->
                    acc.plus(
                        "SampleReturnMessage(message=Message $i) start\nSampleReturnMessage(message=Message $i) end\n"
                    )
                }.trim()
    }
}
