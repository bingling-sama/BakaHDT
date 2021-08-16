package cn.booling.bakahdt.command

import cn.booling.bakahdt.*
import kotlinx.serialization.*
import net.mamoe.mirai.event.events.*

var commands = mutableMapOf<String, Command>()
var simpleCommands = mutableMapOf<String, SimpleCommand>()

@Serializable
data class Command(
    val name: String,
    val info: String? = null,
    val permission: Permission = Permission.MEMBER,
    var execute: suspend (event: MessageEvent) -> Unit
) {
    init {
        commands[name] = this
        if (info != null) TextFields.HELP = TextFields.HELP.plus("&$name  $info\n")
    }
}

@Serializable
data class SimpleCommand(
    val name: String,
    val info: String? = null,
    val permission: String = "member",
    val messages: List<String>
)

@Serializable
data class CommandMap(var commands: List<SimpleCommand> = mutableListOf())
