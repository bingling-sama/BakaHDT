package cn.booling.bakahdt

import cn.booling.bakahdt.command.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import net.mamoe.mirai.*
import net.mamoe.mirai.utils.*
import java.io.*

val BAKA = BotFactory.newBot(BOT_ID, BOT_PWD) {
    fileBasedDeviceInfo()
    autoReconnectOnForceOffline()
    protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE
}
val logger = BAKA.logger

suspend fun main() {
    init()
    BAKA.login()
    BAKA.eventChannel.registerListenerHost(BakaListener())
}

fun init() {
    generateFile("permissions.json")
    generateFile("commands.json")
    permissionMap = Json.decodeFromString(File("permissions.json").readText())
    val commandMap = Json.decodeFromString<CommandMap>(File("commands.json").readText())
    commandMap.commands.forEach { cmd ->
        commands[cmd.name] = cmd.toCommand()
    }
}
