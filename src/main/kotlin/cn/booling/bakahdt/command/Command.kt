package cn.booling.bakahdt.command

import cn.booling.bakahdt.*
import kotlinx.serialization.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import java.io.*

var simpleCommands = mutableMapOf<String, SimpleCommand>()

@Serializable
data class SimpleCommand(
    val name: String,
    val info: String? = null,
    val permission: String = "member",
    val messages: MutableList<String> = mutableListOf()
)

@Serializable
data class CommandMap(var commands: List<SimpleCommand> = mutableListOf())

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
    var message = ""
    this.forEach {
        message += "$it\n"
    }
    message = message.trimEnd('\n')
    return message
}
