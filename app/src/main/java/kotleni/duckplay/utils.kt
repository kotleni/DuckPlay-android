package kotleni.duckplay

import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels

fun download(link: String, path: String) {
    URL(link).openStream().use { input ->
        FileOutputStream(File(path)).use { output ->
            input.copyTo(output)
        }
    }
}