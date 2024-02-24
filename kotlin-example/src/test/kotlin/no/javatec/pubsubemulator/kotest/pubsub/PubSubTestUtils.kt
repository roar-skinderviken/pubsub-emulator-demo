package no.javatec.pubsubemulator.kotest.pubsub

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayOutputStream
import java.io.PrintStream

object PubSubTestUtils {

    /**
     * Handy method for comparing println output
     */
    fun tapSystemOut(body: suspend () -> Unit): String {
        val reroutedOutputStream = ByteArrayOutputStream()
        val originalPrintStream = System.out

        /** re-route output */
        System.setOut(PrintStream(reroutedOutputStream))

        runBlocking(Dispatchers.IO) {
            launch {
                body()
            }
        }

        /** restore output */
        System.setOut(originalPrintStream)

        return reroutedOutputStream.toString().trim()
    }
}
