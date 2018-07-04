package no.soperasteria.powerofsharing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.jsoup.Jsoup
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import kotlin.concurrent.thread

/**
 * RecyclerView adapter extended with project-specific required methods.
 */

class RecyclerAdapter(context: Context, private val notify: () -> Unit) : RecyclerView.Adapter<RecyclerAdapter.AdapterHolder>() {

    private val speakers = mutableListOf<Speaker>()
    private val photos = HashMap<Int, Bitmap>()
    private val applicationPath = context.filesDir

    init {
        getItems()
    }

    private fun readSpeakers() {

        Jsoup.connect("https://powerofsharing.no/").get().run {
            select(".speakers__person").forEachIndexed { _, element ->
                val name = element.select(".speakers__name").text()
                val post = element.select(".speakers__post").text()
                val style = element.select(".speakers__photo").attr("style")
                val photo = style.substring(style.indexOf("https://"), style.indexOf(".jpg") + 4)
                val details = element.attr("href")

                speakers.add(Speaker(name = name, post = post, photo = photo, details = details))
            }
        }

        notify()
    }

    inner class AdapterHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var name: TextView = itemView.findViewById(R.id.name) as TextView
        internal var post: TextView = itemView.findViewById(R.id.post) as TextView
        internal var photo: ImageView = itemView.findViewById(R.id.photo) as ImageView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {

        }
    }

    private fun getItems() {
        thread {
            readSpeakers()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val speakerView = inflater.inflate(R.layout.item_speaker, parent, false)
        return AdapterHolder(speakerView)
    }

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {
        val speaker = getItem(position) ?: return

        holder.name.text = speaker.name
        holder.post.text = speaker.post

        val bitmap = getPhoto(position)
        if (bitmap != null) {
            holder.photo.setImageBitmap(bitmap)
        } else {
            thread {
                val bmp = load(speaker.photo)
                if (bmp != null) {
                    photos[position] = bmp
                    notify()
                } else {
                    download(speaker.photo)
                }
            }
        }
    }

    private fun getPhoto(position: Int): Bitmap? {
        return if (position < photos.size) {
            photos[position]
        } else null
    }

    override fun getItemCount(): Int {
        return speakers.size
    }

    private fun getItem(position: Int): Speaker? {
        return if (0 < position || position <= itemCount) {
            speakers[position]
        } else null
    }

    private fun photoFile(filename: String): File {
        return File(applicationPath, File.separator + filename)
    }

    private fun download(link: String) {
        val input = URL(link).openStream()
        val path = link.substringAfterLast("/")
        val output = FileOutputStream(photoFile(path))
        input.use { _ ->
            output.use { _ ->
                input.copyTo(output)
                notify()
            }
        }
    }

    private fun load(link: String): Bitmap? {
        val path = link.substringAfterLast("/")
        val file = photoFile(path)
        return if (file.exists()) file.inputStream().use {
            BitmapFactory.decodeStream(it)
        } else null
    }
}
