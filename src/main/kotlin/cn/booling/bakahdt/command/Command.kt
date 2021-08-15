package cn.booling.bakahdt.command

import cn.booling.bakahdt.*
import kotlinx.serialization.*
import net.mamoe.mirai.event.events.*

var commands = mutableMapOf<String, Command>()

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
) {
    fun toCommand(): Command {
        var msg = ""
        messages.forEach { message ->
            msg += message + "\n"
        }
        return Command(this.name, this.info, this.permission.getPermission()) { event -> event.simpleReply(msg) }
    }
}

@Serializable
data class CommandMap(var commands: List<SimpleCommand> = mutableListOf())
