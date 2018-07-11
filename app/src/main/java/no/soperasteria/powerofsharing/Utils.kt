package no.soperasteria.powerofsharing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import no.soperasteria.powerofsharing.data.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.replace
import org.jetbrains.anko.db.select
import org.jsoup.Jsoup
import java.io.File
import java.io.FileOutputStream
import java.net.URL

const val TAG = "PowerUtils"

fun fileForLink(link: String): String {
    return link.substringAfterLast("/")
}

fun photoFile(path: File, filename: String): File {
    return File(path, File.separator + filename)
}

fun download(path: File, link: String, notify: () -> Unit) {
    val input = URL(link).openStream()
    val output = FileOutputStream(path)
    input.use { _ ->
        output.use { _ ->
            input.copyTo(output)
            notify()
        }
    }
}

fun load(file: File): Bitmap? {
    return if (file.exists()) file.inputStream().use {
        BitmapFactory.decodeStream(it)
    } else null
}

fun drawableFor(file: File): Drawable? {
    return if (file.exists()) file.inputStream().use { inputStream ->
        Drawable.createFromStream(inputStream, null)
    } else null
}

@Synchronized
fun readSpeakers(context: Context) {
    fetchSpeakers().let { speakers ->
        context.database.use {
            for (speaker in speakers) {

                replace("Speaker",
                        "name" to speaker.name,
                        "post" to speaker.post,
                        "photo" to speaker.photo,
                        "details" to speaker.details)
            }

        }
    }
    Log.d(TAG, "Done reading speakers to database")
}

@Synchronized
fun loadSpeakers(context: Context): List<Speaker> {
    Log.d(TAG, "Ready to load from database")
    return context.database.use {
        select("Speaker").exec {
            parseList(classParser<Speaker>())
        }
    }
}

fun fetchSpeakers(): List<Speaker> {
    val speakers = mutableListOf<Speaker>()
    Jsoup.connect("https://powerofsharing.no/").get().run {
        select(".speakers__person").forEachIndexed { _, element ->
            val name = element.select(".speakers__name").text()
            val post = element.select(".speakers__post").text()
            val style = element.select(".speakers__photo").attr("style")
            val photo = style.substring(style.indexOf("https://"), style.indexOf(".jpg") + 4)
            val detailsUrl = element.attr("href")

            Jsoup.connect(detailsUrl).get().run {
                val details = select(".speaker-profile .content").map{
                    it.text()
                }.reduce { a,b -> "$a$b" }
                speakers.add(Speaker(name = name, post = post, photo = photo, details = details))
            }

        }
    }
    return speakers
}
