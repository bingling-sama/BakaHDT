package cn.booling.bakahdt

import net.mamoe.mirai.*
import net.mamoe.mirai.utils.*
import java.io.*

val IDENTIFIER = "/"

val BAKA = BotFactory.newBot(BOT_ID, BOT_PWD) {
    enableContactCache()
    fileBasedDeviceInfo()
    autoReconnectOnForceOffline()
    protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE
}
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
