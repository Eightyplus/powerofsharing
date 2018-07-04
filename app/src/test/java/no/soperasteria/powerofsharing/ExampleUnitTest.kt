package no.soperasteria.powerofsharing

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun speaker_isParsed() {
        val speakers = MainActivity().readSpeakers()


        for (speaker in speakers)
            println(speaker)

        assertTrue(speakers.size > 0)
    }

}
