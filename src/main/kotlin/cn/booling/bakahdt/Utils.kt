package cn.booling.bakahdt

import cn.booling.bakahdt.command.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.message.data.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.*
import java.text.*
import java.util.*
import kotlin.random.*

fun getImage(url: String, file: File): ByteArray {
    val okHttpClient = OkHttpClient()
    val request = Request.Builder().url(url).build()
    val imageByte = okHttpClient.newCall(request).execute().body!!.bytes()
    val fileParent = file.parentFile
    if (!fileParent.exists()) fileParent.mkdir()
        file.writeBytes(imageByte)
    return imageByte
}

fun getRequest(url: String): String {
    val okHttpClient = OkHttpClient()
    val request = Request.Builder().url(url).build()
    return okHttpClient.newCall(request).execute().body!!.string()
}

fun parseWebsiteBody(parseWebBody : String): Document {
    return Jsoup.parse(parseWebBody)
}

fun getDocument(url: String) : Document {
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

fun loadPermissionMap() {
    permissionMap = Json.decodeFromString(File("permissions.json").readText())
    logger.info("Permissions Reloaded")
}

fun loadSimpleCommands() {
    val commandMap = Json.decodeFromString<CommandMap>(File("commands.json").readText())
    commandMap.commands.forEach { cmd ->
        if (cmd.messages.isNotEmpty()) simpleCommands[cmd.name] = cmd
        if (cmd.info != null) TextFields.HELP = TextFields.HELP.plus("\n$IDENTIFIER${cmd.name}  ${cmd.info}")
        logger.info("Loaded command: ${cmd.name} ${cmd.info}")
    }
    logger.info("Simple Commands Reloaded")
}

fun List<String>.toMessage(): String {
    if (this.size != 1) {
        var ret = ""
        this.forEach { ret += it + "\n" }
        return ret
    } else {
        return this[0]
    }
}

suspend fun MessageEvent.simpleReply(message: String) = this.subject.sendMessage(message)
suspend fun MessageEvent.simpleReply(message: MessageChain) = this.subject.sendMessage(message)

fun MessageEvent.jrrp(): String {
    val random = Random(
        (SimpleDateFormat("yyMMdd").format(Date()).toInt() xor this.sender.id.toInt()).toLong()
    )
    return "${this.sender.nick} 今天的人品值是：${random.nextInt(101)}"
}