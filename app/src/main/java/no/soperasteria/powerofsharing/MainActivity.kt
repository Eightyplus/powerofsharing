package no.soperasteria.powerofsharing

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import org.jsoup.Jsoup
import kotlin.concurrent.thread

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        thread() {
            readSpeakers()
        }

        return super.onTouchEvent(event)
    }


    fun readSpeakers(): List<Speaker> {
        val speakers = mutableListOf<Speaker>()

        Jsoup.connect("https://powerofsharing.no/").get().run {
            select(".speakers__person").forEachIndexed { index, element ->
                val name = element.select(".speakers__name").text()
                val post = element.select(".speakers__post").text()
                val photo = element.select(".speakers__photo").text()
                val details = element.attr("href")

                speakers.add(Speaker(name = name, post = post, photo = photo, details = details))

                println(speakers.last())
            }
        }

        return speakers
    }
}
