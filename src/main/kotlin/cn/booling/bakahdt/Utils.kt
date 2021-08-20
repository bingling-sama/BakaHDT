package cn.booling.bakahdt

import cn.booling.bakahdt.command.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.message.data.*
import java.io.*
import java.text.*
import java.util.*
import kotlin.random.*

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
