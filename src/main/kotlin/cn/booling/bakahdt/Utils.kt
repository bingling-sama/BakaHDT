package cn.booling.bakahdt

import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.utils.ExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.File
import java.io.InputStream
import java.util.*

fun MessageEvent.hasUrl(): Boolean {
    return this.message.contentToString().contains("http")
}

fun readImage(url: String): ExternalResource {
    val base64Prefix = "data:image/png;base64,"
    return if (url.startsWith(base64Prefix)) {
        Base64.getDecoder().decode(url.substring(base64Prefix.length)).toExternalResource()
    } else {
        getImage(
            when {
                url.startsWith('/') -> "https://www.mcmod.cn$url"
                url.startsWith("//") -> "https:$url"
                else -> url
            }
        ).toExternalResource()
    }
}

fun getImage(url: String): InputStream {
    val okHttpClient = OkHttpClient()
    val request = Request.Builder().url(url).build()
    return okHttpClient.newCall(request).execute().body!!.byteStream()
}

fun getRequest(url: String): String {
    val okHttpClient = OkHttpClient()
    val request = Request.Builder().url(url).build()
    return okHttpClient.newCall(request).execute().body!!.string()
}

fun parseWebsiteBody(parseWebBody: String): Document {
    return Jsoup.parse(parseWebBody)
}

fun getDocument(url: String): Document {
    return parseWebsiteBody(getRequest(url))
}

fun documentElementSelect(document: Document, cssQuery: String): Elements {
    return document.select(cssQuery)
}

fun String.substringBetween(open: String, close: String): String {
    val start = this.indexOf(open)
    if (start != -1) {
        val end = this.indexOf(close, start + open.length)
        if (end != -1) {
            return this.substring(start + open.length, end)
        }
    }
    return ""
}

fun checkFileExists(fileName: String): Boolean {
    val file = File(fileName)
    if (!file.exists()) {
        file.createNewFile()
        return false
    }
    return true
}

fun generateFile(fileName: String) {
    if (checkFileExists(fileName)) {
        logger.info("$fileName found, loading...")
    } else {
        logger.info("Can not find ${fileName}, generating...")
        getResourceAsText(fileName).let { File(fileName).writeText(it, Charsets.UTF_8) }
    }
}

fun getResourceAsText(path: String): String {
    return object {}::class.java.getResourceAsStream("/${path}")!!.bufferedReader().readText()
}
