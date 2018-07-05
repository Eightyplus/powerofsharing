package no.soperasteria.powerofsharing

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream
import java.net.URL

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

fun load(file: File, link: String): Bitmap? {
    return if (file.exists()) file.inputStream().use {
        BitmapFactory.decodeStream(it)
    } else null
}