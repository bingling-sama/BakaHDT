package cn.booling.bakahdt

import cn.booling.bakahdt.command.loadPermissionMap
import cn.booling.bakahdt.command.loadSimpleCommands
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.GroupEvent
import net.mamoe.mirai.utils.BotConfiguration
import java.io.File

const val IDENTIFIER = "/"

val BAKA = BotFactory.newBot(BOT_ID, BOT_PWD) {
    enableContactCache()
    fileBasedDeviceInfo()
    autoReconnectOnForceOffline()
    protocol = BotConfiguration.MiraiProtocol.ANDROID_WATCH
//    protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE
}
val TWEAKER_CHANNEL =
    GlobalEventChannel.filter { ev -> ev is GroupEvent && ev.group == BAKA.getGroupOrFail(624487948L) }
val logger = BAKA.logger
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
