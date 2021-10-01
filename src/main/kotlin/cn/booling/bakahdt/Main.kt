package cn.booling.bakahdt

import cn.booling.bakahdt.command.*
import net.mamoe.mirai.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.utils.*
import java.io.*

const val IDENTIFIER = "/"

val BAKA = BotFactory.newBot(BOT_ID, BOT_PWD) {
    enableContactCache()
    fileBasedDeviceInfo()
    autoReconnectOnForceOffline()
    protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE
}
val TWEAKER_CHANNEL =
    GlobalEventChannel.filter { ev -> ev is GroupEvent && ev.group == BAKA.getGroupOrFail(624487948L) }
val logger = BAKA.logger
var commandsFile: File = File("commands.json")
var permissionsFile: File = File("permissions.json")

suspend fun main() {
    init()
    BAKA.login()
    cmdInit()
}

fun init() {
    generateFile("permissions.json")
    generateFile("commands.json")
    loadPermissionMap()
    loadSimpleCommands()
}
